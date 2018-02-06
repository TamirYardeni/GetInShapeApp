package tmrapps.getinshapeapp.Exercise.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.List;

import tmrapps.getinshapeapp.ExerciseList.ExerciseListViewModel;
import tmrapps.getinshapeapp.ExerciseList.Model.ExerciseListItem;
import tmrapps.getinshapeapp.ExerciseList.Model.ExerciseListItemRepository;
import tmrapps.getinshapeapp.GetInShapeApp;
import tmrapps.getinshapeapp.Main.AppLocalStore;

/**
 * Created by tamir on 2/5/2018.
 */

public class ExerciseReposirory {
    public static final ExerciseReposirory instance = new ExerciseReposirory();

    MutableLiveData<Exercise> exerciseLiveData;

    MutableLiveData<List<Exercise>> exercisesLiveDT;

    ExerciseReposirory() {

    }

    public LiveData<Exercise> getExerciseById(String exerciseId) {
        synchronized (this) {
            if (this.exerciseLiveData == null) {
                this.exerciseLiveData = new MutableLiveData<Exercise>();

                // Get the last update date
                long lastUpdateDate = 0;
                try {
                    lastUpdateDate = GetInShapeApp.getMyContext()
                            .getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("lastUpdateDateExercise", 0);
                } catch (Exception e) {

                }

                ExerciseFirebase.getExerciseByIdAndObserve(lastUpdateDate, exerciseId, new ExerciseFirebase.OnExerciseListener() {
                    @Override
                    public void onComplete(Exercise exercise) {
                        updateExerciseDataInLocalStore(exercise, exerciseId);
                    }
                });
            }
        }

        return this.exerciseLiveData;
    }

    public void addExercise(Exercise exercise) {
        ExerciseFirebase.addExercise(exercise, new ExerciseFirebase.OnExerciseListener() {
            @Override
            public void onComplete(Exercise exercise) {
                //String categoryId, String exerciseName, String exerciseId
                ExerciseListItemRepository.instance.
                        addExerciseListItemOfCategory(exercise.getCategoryId(), exercise.getName(), exercise.getId());
            }
        });
    }

    private void updateExerciseDataInLocalStore(Exercise data, String exerciseId) {
        GetExerciseListTask exerciseListTask = new GetExerciseListTask();
        GetExerciseTaskParams getExerciseTatkParams = new GetExerciseTaskParams(exerciseId, data);
        exerciseListTask.execute(getExerciseTatkParams);
    }

    private static class GetExerciseTaskParams {
        String exerciseId;
        Exercise data;

        GetExerciseTaskParams(String exerciseId, Exercise data) {
            this.exerciseId = exerciseId;
            this.data = data;
        }
    }

    class GetExerciseListTask extends AsyncTask<GetExerciseTaskParams, String, Exercise> {

        @Override
        protected Exercise doInBackground(GetExerciseTaskParams[] lists) {

            if (lists.length > 0) {

                SharedPreferences sharedPreferences = GetInShapeApp.getMyContext()
                        .getSharedPreferences("TAG", Context.MODE_PRIVATE);

                Exercise data = lists[0].data ;
                long lastUpdateDate = 0;
                try {
                    lastUpdateDate = sharedPreferences.getLong("lastUpdateDateExercise", 0);
                } catch (Exception ex) {

                }

                if (data != null) {
                    long recentUpdate = lastUpdateDate;
                    AppLocalStore.db.exerciseDao().insertAll(data);
                    if (data.getLastUpdateDate() > recentUpdate) {
                        recentUpdate = data.getLastUpdateDate();
                    }

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    SharedPreferences.Editor editor1 = sharedPreferences.edit();
                    editor1.putLong("lastUpdateDateExercise", recentUpdate);
                    editor1.commit();
                }
                Exercise exercise= AppLocalStore.db.exerciseDao().getExerciseDetails(lists[0].exerciseId);

                return exercise;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Exercise exercise) {
            super.onPostExecute(exercise);
            exerciseLiveData.setValue(exercise);
        }
    }
}
