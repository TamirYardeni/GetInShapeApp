package tmrapps.getinshapeapp.PersonalArea.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import tmrapps.getinshapeapp.Main.AppLocalStore;



/**
 * Created by nathan on 31/01/2018.
 */

public class PersonalAreaRepository {

    public final static PersonalAreaRepository instance = new PersonalAreaRepository();

    private PersonalAreaRepository(){

    }

    MutableLiveData<PersonalInformation> personalInfoliveData;

    public LiveData<PersonalInformation> getPersonalInformation(String userId){
        synchronized (this) {
            if (personalInfoliveData == null) {
                personalInfoliveData = new MutableLiveData<>();
                PersonalAreaFirebase.getPersonalInformationByUserId(userId, (personalInformation) -> {
                    if (personalInformation != null)
                        personalInfoliveData.setValue(personalInformation);
                });
            }
        }

        return personalInfoliveData;
    }

    /*public LiveData<PersonalInformation> getPersonalInformation(String userId){
        synchronized (this) {
            if (personalInfoliveData == null) {
                personalInfoliveData = new MutableLiveData<>();
                GetPersonalInformationTask task = new GetPersonalInformationTask();
                task.execute(userId);
            }
        }

        return personalInfoliveData;
    }
*/
    public void savePersonalInformation(PersonalInformation personalInformation){
        PersonalAreaFirebase.savePersonalInformation(personalInformation, (data) -> {
           // AppLocalStore.db.personalInformationDAO().savePersonalInformation(data);
        });
    }
/*
    class GetPersonalInformationTask extends AsyncTask<String,String,PersonalInformation> {
        @Override
        protected PersonalInformation doInBackground(String[] lists) {
            if (lists.length > 0) {
                String userId = lists[0];
                 PersonalInformation info = AppLocalStore.db.personalInformationDAO().getPersonalInformation(userId);

                if (info == null) {
                    PersonalAreaFirebase.getPersonalInformationByUserId(userId, (personalInformation) -> {
                        if (personalInformation != null)
                            info.setUserId(personalInformation.userId);
                            info.setHourTrain(personalInformation.hourTrain);
                            info.setMinuteTrain(personalInformation.minuteTrain);
                    });
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
    */

}

