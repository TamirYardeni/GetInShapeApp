package tmrapps.getinshapeapp.PersonalArea.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import tmrapps.getinshapeapp.GetInShapeApp;
import tmrapps.getinshapeapp.Main.AppLocalStore;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by nathan on 31/01/2018.
 */

public class PersonalAreaRepository {

    public final static PersonalAreaRepository instance = new PersonalAreaRepository();

    private PersonalAreaRepository() {

    }

    MutableLiveData<PersonalInformation> personalInfoliveData;

    public LiveData<PersonalInformation> getPersonalInformation(String userId) {
        synchronized (this) {
            if (personalInfoliveData == null) {
                personalInfoliveData = new MutableLiveData<>();

                long lastUpdateDate = 0;
                try {
                    lastUpdateDate = GetInShapeApp.getMyContext()
                            .getSharedPreferences("TAG", MODE_PRIVATE).getLong("lastUpdateDate", 0);
                } catch (Exception e) {

                }

                PersonalAreaFirebase.getPersonalInformationByUserId(userId, lastUpdateDate, (personalInformation) -> {
                    updateExerciseDataInLocalStore(personalInformation, userId);
                });
            }
        }

        return personalInfoliveData;
    }

    public void savePersonalInformation(PersonalInformation personalInformation) {
        PersonalAreaFirebase.savePersonalInformation(personalInformation, (data) -> {
            SavePersonalInformationTask task = new SavePersonalInformationTask();
            task.execute(data);
        });
    }

    private void updateExerciseDataInLocalStore(PersonalInformation data, String userId) {
        GetPersonalInformationTask exerciseListTask = new GetPersonalInformationTask();
        GetPersonalAreaTaskParams getExerciseTaskParams = new GetPersonalAreaTaskParams(userId, data);
        exerciseListTask.execute(getExerciseTaskParams);
    }

    private static class GetPersonalAreaTaskParams {
        String userId;
        PersonalInformation data;

        GetPersonalAreaTaskParams(String userId, PersonalInformation data) {
            this.userId = userId;
            this.data = data;
        }
    }

    class GetPersonalInformationTask extends AsyncTask<GetPersonalAreaTaskParams, String, PersonalInformation> {
        @Override
        protected PersonalInformation doInBackground(GetPersonalAreaTaskParams[] lists) {
            if (lists.length > 0) {
                GetPersonalAreaTaskParams data = lists[0];

                if (data.data != null) {
                    AppLocalStore.db.personalInformationDAO().savePersonalInformation(data.data);
                }

                PersonalInformation info = AppLocalStore.db.personalInformationDAO().getPersonalInformation(data.userId);

                if (info != null) {
                    SharedPreferences.Editor editor = GetInShapeApp.getMyContext().getSharedPreferences("TAG", MODE_PRIVATE).edit();

                    editor.putLong("lastUpdateDate", info.lastUpdate);
                    editor.commit();
                }

                return info;
            }

            return null;
        }

        @Override
        protected void onPostExecute(PersonalInformation information) {
            super.onPostExecute(information);
            personalInfoliveData.setValue(information);
        }
    }


    class SavePersonalInformationTask extends AsyncTask<PersonalInformation, String, PersonalInformation> {

        @Override
        protected PersonalInformation doInBackground(PersonalInformation[] lists) {
            if (lists.length > 0) {
                PersonalInformation data = lists[0];
                AppLocalStore.db.personalInformationDAO().savePersonalInformation(data);

                return  data;
            }

            return null;
        }
    }
}

