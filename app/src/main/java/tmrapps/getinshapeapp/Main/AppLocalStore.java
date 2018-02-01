package tmrapps.getinshapeapp.Main;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import tmrapps.getinshapeapp.PersonalArea.Model.PersonalInformationDAO;
import tmrapps.getinshapeapp.PersonalArea.Model.PersonalInformation;

/**
 * Created by tamir on 1/19/2018.
 */

@Database(entities = {PersonalInformation.class}, version = 0)
abstract class AppLocalStoreDb extends RoomDatabase {
    public abstract PersonalInformationDAO personalInformationDAO();
}

public class AppLocalStore{
    /*static public AppLocalStoreDb db = Room.databaseBuilder(MyApplication.getMyContext(),
            AppLocalStoreDb.class,
            "database-name").fallbackToDestructiveMigration().build();*/
}
