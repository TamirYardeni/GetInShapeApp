package tmrapps.getinshapeapp.Main;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import tmrapps.getinshapeapp.PersonalArea.Model.PersonalInformation;
import tmrapps.getinshapeapp.PersonalArea.Model.PersonalInformationDAO;

/**
 * Created by nathan on 03/02/2018.
 */

@Database(entities = {PersonalInformation.class}, version = 1)
public abstract class AppLocalStoreDb extends RoomDatabase {
    public abstract PersonalInformationDAO personalInformationDAO();
}