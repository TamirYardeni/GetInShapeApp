package tmrapps.getinshapeapp.ExerciseList;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import tmrapps.getinshapeapp.ExerciseList.Model.ExerciseListItem;
import tmrapps.getinshapeapp.ExerciseList.Model.ExerciseListItemRepository;

/**
 * Created by tamir on 2/5/2018.
 */

public class ExerciseListViewModel extends ViewModel {
    private LiveData<ExerciseListItem> exerciseListItems;

    public ExerciseListViewModel() {

    }

    public LiveData<ExerciseListItem> getExerciseOfCategory(String categoryId) {
        this.exerciseListItems = ExerciseListItemRepository.instance.getExerciseOfCategory(categoryId);
        return this.exerciseListItems;
    }

    public void addExerciseListItem(String categoryId, String exerciseName, String exerciseId) {
        ExerciseListItemRepository.instance.addExerciseListItemOfCategory(categoryId, exerciseName, exerciseId );
    }
}
