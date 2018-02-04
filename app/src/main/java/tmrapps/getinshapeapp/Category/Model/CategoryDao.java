package tmrapps.getinshapeapp.Category.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by tamir on 2/4/2018.
 */
@Dao
public interface CategoryDao {

    @Query("SELECT * FROM Category")
    List<Category> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Category... categories);
}
