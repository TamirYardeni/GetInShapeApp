package tmrapps.getinshapeapp.Exercise;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

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

    public ExerciseViewModel() {

    }

    /*public void getAll() {
        ExerciseReposirory.instance.getAll();
    }*/

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
}
