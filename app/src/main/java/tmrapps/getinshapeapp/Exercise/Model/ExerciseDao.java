package tmrapps.getinshapeapp.Exercise.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by tamir on 2/5/2018.
 */
@Dao
public interface ExerciseDao {
    @Query("SELECT * FROM Exercise WHERE id=:id")
    Exercise getExerciseDetails(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Exercise... exercises);
}
