package tmrapps.getinshapeapp.Exercise;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import tmrapps.getinshapeapp.Exercise.Model.Exercise;
import tmrapps.getinshapeapp.Exercise.Model.ExerciseReposirory;

/**
 * Created by tamir on 2/5/2018.
 */

public class ExerciseViewModel extends ViewModel{
    private LiveData<Exercise> exercise;
    private LiveData<List<Exercise>> exercises;

    public ExerciseViewModel() {

    }

    public LiveData<Exercise> getExerciseById(String exerciseId) {
        this.exercise = ExerciseReposirory.instance.getExerciseById(exerciseId);
        return this.exercise;
    }

    public void addExercise(Exercise exercise) {
        ExerciseReposirory.instance.addExercise(exercise);
    }
}
