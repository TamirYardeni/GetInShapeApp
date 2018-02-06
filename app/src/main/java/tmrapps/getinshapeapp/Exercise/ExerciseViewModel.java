package tmrapps.getinshapeapp.Exercise;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;

import java.util.List;

import tmrapps.getinshapeapp.Exercise.Model.Exercise;
import tmrapps.getinshapeapp.Exercise.Model.ExerciseReposirory;
import tmrapps.getinshapeapp.ExerciseList.Model.ExerciseInCategory;

/**
 * Created by tamir on 2/5/2018.
 */

public class ExerciseViewModel extends ViewModel{
    private LiveData<Exercise> exercise;

    private LiveData<List<ExerciseInCategory>> exercisesByCategory;

    private LiveData<String> imgUrl;

    public ExerciseViewModel() {

    }

    public interface OnSaveImageListener {
        void complete(String url);
        void fail();
    }

    public LiveData<Exercise> getExerciseById(String exerciseId) {
        this.exercise = ExerciseReposirory.instance.getExerciseById(exerciseId);
        return this.exercise;
    }

    public LiveData<List<ExerciseInCategory>> getExerciseByCategory(String categoryId) {
        this.exercisesByCategory = ExerciseReposirory.instance.getExerciseByCategory(categoryId);
        return this.exercisesByCategory;
    }

    public void addExercise(Exercise exercise) {
        ExerciseReposirory.instance.addExercise(exercise);
    }

    public LiveData<String> addExerciseImage(String exerciseId, Bitmap image) {
        this.imgUrl = ExerciseReposirory.instance.addExerciseImage(exerciseId, image);
        return this.imgUrl;
    }
}
