package tmrapps.getinshapeapp.ExerciseList.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.List;

import tmrapps.getinshapeapp.Exercise.Model.ExerciseFirebase;
import tmrapps.getinshapeapp.Exercise.Model.ExerciseReposirory;
import tmrapps.getinshapeapp.GetInShapeApp;
import tmrapps.getinshapeapp.Main.AppLocalStore;

/**
 * Created by tamir on 2/5/2018.
 */

public class ExerciseListItemRepository {
    public static final ExerciseListItemRepository instance = new ExerciseListItemRepository();

    MutableLiveData<ExerciseListItem> exerciseOfCategoryLiveData;

    ExerciseListItemRepository() {

    }

    public LiveData<ExerciseListItem> getExerciseOfCategory(String categoryId) {
        synchronized (this) {
            if (this.exerciseOfCategoryLiveData == null) {
                this.exerciseOfCategoryLiveData = new MutableLiveData<ExerciseListItem>();

                // Get the last update date
                long lastUpdateDate = 0;
                try {
                    lastUpdateDate = GetInShapeApp.getMyContext()
                            .getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("lastUpdateDateExerciseList", 0);
                } catch (Exception e) {

                }

                ExerciseListFirebase.getExercisesOfCategoryAndObserve(lastUpdateDate,categoryId, (data) -> {
                    updateExerciseOfCategoryDataInLocalStore(data, categoryId);
                });
            }
        }

        return this.exerciseOfCategoryLiveData;
    }

    public void addExerciseListItemOfCategory(String categoryId, String exerciseName, String exerciseId) {
        ExerciseListFirebase.addExerciseListItem(categoryId, exerciseName, exerciseId);
    }

    private void updateExerciseOfCategoryDataInLocalStore(List<ExerciseListItem> data, String categoryId) {
        GetExerciseListTatk exerciseListTatk = new GetExerciseListTatk();
        GetExerciseListTaskParams getExerciseListTatkParams = new GetExerciseListTaskParams(categoryId, data);
        exerciseListTatk.execute(getExerciseListTatkParams);
    }

    private static class GetExerciseListTaskParams {
        String categoryId;
        List<ExerciseListItem> data;

        GetExerciseListTaskParams(String categoryId, List<ExerciseListItem> data) {
            this.categoryId = categoryId;
            this.data = data;
        }
    }

    class GetExerciseListTatk extends AsyncTask<GetExerciseListTaskParams, String, ExerciseListItem> {

        @Override
        protected ExerciseListItem doInBackground(GetExerciseListTaskParams[] lists) {

            if (lists.length > 0) {

                SharedPreferences sharedPreferences = GetInShapeApp.getMyContext()
                        .getSharedPreferences("TAG", Context.MODE_PRIVATE);

                List<ExerciseListItem> data = lists[0].data ;
                long lastUpdateDate = 0;
                try {
                    lastUpdateDate = sharedPreferences.getLong("lastUpdateDateExerciseList", 0);
                } catch (Exception ex) {

                }

                if (data != null && data.size() > 0) {
                    long recentUpdate = lastUpdateDate;
                    for (ExerciseListItem exerciseListItem : data) {
                        AppLocalStore.db.exerciseListItemDao().insertAll(exerciseListItem);
                        if (exerciseListItem.getLastUpdateDate() > recentUpdate) {
                            recentUpdate = exerciseListItem.getLastUpdateDate();
                        }
                    }

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    SharedPreferences.Editor editor1 = sharedPreferences.edit();
                    editor1.putLong("lastUpdateDateExerciseList", recentUpdate);
                    editor1.commit();
                }
                ExerciseListItem exerciseListItem = AppLocalStore.db.exerciseListItemDao().getExerciseOfCategory(lists[0].categoryId);

                return exerciseListItem;
            }
            return null;
        }

        @Override
        protected void onPostExecute(ExerciseListItem exerciseListItem) {
            super.onPostExecute(exerciseListItem);
            exerciseOfCategoryLiveData.setValue(exerciseListItem);
        }
    }
}
