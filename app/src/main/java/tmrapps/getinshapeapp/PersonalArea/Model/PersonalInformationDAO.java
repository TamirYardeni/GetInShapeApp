package tmrapps.getinshapeapp.PersonalArea.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

/**
 * Created by nathan on 01/02/2018.
 */

@Dao
public interface PersonalInformationDAO {

    @Query("Select * from PersonalInformation where userId = :userId")
    PersonalInformation getPersonalInformation(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePersonalInformation(PersonalInformation info);
}
