package tmrapps.getinshapeapp.ExerciseList.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import tmrapps.getinshapeapp.ExerciseList.Model.ExerciseListItem;

/**
 * Created by tamir on 2/5/2018.
 */
@Dao
public interface ExerciseListItemDao {
    @Query("SELECT * FROM ExerciseListItem WHERE categoryId=:categoryId")
    ExerciseListItem getExerciseOfCategory(String categoryId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ExerciseListItem... categories);
}
