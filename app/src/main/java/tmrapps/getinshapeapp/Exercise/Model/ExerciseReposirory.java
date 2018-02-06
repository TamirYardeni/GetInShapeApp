package tmrapps.getinshapeapp.Exercise.Model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.webkit.URLUtil;

import java.util.ArrayList;
import java.util.List;

import tmrapps.getinshapeapp.ExerciseList.Model.ExerciseInCategory;
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

    MutableLiveData<List<ExerciseInCategory>> exercisesByCategoryLiveData;

    MutableLiveData<String> imageUrlLiveData;

    ExerciseReposirory() {

    }

    public LiveData<Exercise> getExerciseById(String exerciseId) {
        synchronized (this) {
            if (this.exerciseLiveData == null) {
                this.exerciseLiveData = new MutableLiveData<Exercise>();
            }

            GetExerciseByIdTask GetExerciseByIdTask = new GetExerciseByIdTask();
            GetExerciseByIdTask.execute(exerciseId);
        }

        return this.exerciseLiveData;
    }

    public LiveData<List<ExerciseInCategory>> getExerciseByCategory(String categoryId) {
        if (this.exercisesByCategoryLiveData == null) {
            this.exercisesByCategoryLiveData = new MutableLiveData<List<ExerciseInCategory>>();
            synchronized (this) {
                // Get the last update date
                long lastUpdateDate = 0;
                try {
                    lastUpdateDate = GetInShapeApp.getMyContext()
                            .getSharedPreferences("TAG", Context.MODE_PRIVATE).getLong("lastUpdateDateExercise", 0);
                } catch (Exception e) {

                }

                ExerciseFirebase.getAllExerciseAndObserve(lastUpdateDate, categoryId, new ExerciseFirebase.OnExerciseListener() {
                    @Override
                    public void onComplete(List<Exercise> exerciseList) {
                        updateExerciseDataInLocalStore(exerciseList, categoryId);
                    }

                    @Override
                    public void onComplete() {
                        // No need to implement
                    }
                });
            }
        } else {
            GetExerciseListByCategoryTask exerciseListTask = new GetExerciseListByCategoryTask();
            exerciseListTask.execute(categoryId);
        }

        return this.exercisesByCategoryLiveData;
    }

    public void addExercise(Exercise exercise) {
        ExerciseFirebase.addExercise(exercise, new ExerciseFirebase.OnExerciseListener() {
            @Override
            public void onComplete(List<Exercise> exercise) {
                // No need to implement
            }

            @Override
            public void onComplete() {
                ExerciseListItemRepository.instance.
                        addExerciseListItemOfCategory(exercise.getCategoryId(), exercise.getName(), exercise.getId());
            }
        });
    }

    public interface OnSaveImageListener {
        void complete(String url);
        void fail();
    }

    public LiveData<String> addExerciseImage(String exerciseId, Bitmap image) {
        MutableLiveData<String> urlLiveData = new MutableLiveData<>();
        ExerciseFirebase.saveImage(image, exerciseId, new OnSaveImageListener() {
            @Override
            public void complete(String url) {
                String fileName = URLUtil.guessFileName(url, null, null);
                /*saveImageToFile(imageBmp,fileName);*/
                /*listener.complete(url);*/
                urlLiveData.setValue(fileName);
            }

            @Override
            public void fail() {
                urlLiveData.setValue(null);
            }
        });

        return urlLiveData;
    }

    private void updateExerciseDataInLocalStore(List<Exercise> data, String id) {
        GetExerciseListTask exerciseListTask = new GetExerciseListTask();
        GetExerciseTaskParams getExerciseTatkParams = new GetExerciseTaskParams(id, data);
        exerciseListTask.execute(getExerciseTatkParams);
    }

    private static class GetExerciseTaskParams {
        String id;
        List<Exercise> data;

        GetExerciseTaskParams(String exerciseId, List<Exercise> data) {
            this.id = exerciseId;
            this.data = data;
        }
    }

    /**
     * Get the exercises in category list
     * Before returning the data the function save it to the db
     */
    class GetExerciseListTask extends AsyncTask<GetExerciseTaskParams, String, List<ExerciseInCategory>> {

        @Override
        protected List<ExerciseInCategory> doInBackground(GetExerciseTaskParams[] lists) {

            if (lists.length > 0) {

                SharedPreferences sharedPreferences = GetInShapeApp.getMyContext()
                        .getSharedPreferences("TAG", Context.MODE_PRIVATE);
                List<ExerciseInCategory> exercisesInCategory = new ArrayList<>();
                List<Exercise> data = lists[0].data ;
                long lastUpdateDate = 0;
                try {
                    lastUpdateDate = sharedPreferences.getLong("lastUpdateDateExercise", 0);
                } catch (Exception ex) {

                }

                if (data != null && data.size() > 0) {
                    long recentUpdate = lastUpdateDate;
                    for (Exercise exercise : data) {
                        AppLocalStore.db.exerciseDao().insertAll(exercise);
                        if (exercise.getLastUpdateDate() > recentUpdate) {
                            recentUpdate = exercise.getLastUpdateDate();
                        }
                    }

                    List<Exercise> exercises = AppLocalStore.db.exerciseDao().getExerciseByCategory(lists[0].id);

                    exercises.forEach(exercise -> {
                        ExerciseInCategory exerciseInCategory = new ExerciseInCategory();
                        exerciseInCategory.setId(exercise.getId());
                        exerciseInCategory.setName(exercise.getName());
                        exercisesInCategory.add(exerciseInCategory);
                    });

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.putLong("lastUpdateDateExercise", recentUpdate);
                    editor.commit();
                }
                return exercisesInCategory;
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<ExerciseInCategory> exercises) {
            super.onPostExecute(exercises);
            exercisesByCategoryLiveData.setValue(exercises);
        }
    }

    /**
     * Get the exercises of category from the db
     */
    class GetExerciseListByCategoryTask extends AsyncTask<String, String, List<ExerciseInCategory>> {

        @Override
        protected List<ExerciseInCategory> doInBackground(String[] lists) {

            if (lists.length > 0) {
                String categoryId = lists[0] ;
                List<Exercise> exercises = null;
                List<ExerciseInCategory> exercisesInCategory = new ArrayList<>();
                if (categoryId != null) {
                    exercises= AppLocalStore.db.exerciseDao().getExerciseByCategory(categoryId);
                }

                exercises.forEach(exercise -> {
                    ExerciseInCategory exerciseInCategory = new ExerciseInCategory();
                    exerciseInCategory.setId(exercise.getId());
                    exerciseInCategory.setName(exercise.getName());
                    exercisesInCategory.add(exerciseInCategory);
                });

                return exercisesInCategory;
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<ExerciseInCategory> exercises) {
            super.onPostExecute(exercises);
            exercisesByCategoryLiveData.setValue(exercises);
        }
    }

    /**
     * Get the exercise from the db
     */
    class GetExerciseByIdTask extends AsyncTask<String, String, Exercise> {

        @Override
        protected Exercise doInBackground(String[] lists) {

            if (lists.length > 0) {
                String exerciseId = lists[0] ;
                Exercise exercise = null;
                List<ExerciseInCategory> exercisesInCategory = new ArrayList<>();
                if (exerciseId != null) {
                    exercise = AppLocalStore.db.exerciseDao().getExerciseDetails(exerciseId);
                }

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
