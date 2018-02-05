package tmrapps.getinshapeapp.Main;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import tmrapps.getinshapeapp.Category.Model.Category;
import tmrapps.getinshapeapp.Category.Model.CategoryDao;
import tmrapps.getinshapeapp.PersonalArea.Model.PersonalInformation;
import tmrapps.getinshapeapp.PersonalArea.Model.PersonalInformationDAO;

/**
 * Created by nathan on 03/02/2018.
 */

@Database(entities = {PersonalInformation.class, Category.class}, version = 2)
public abstract class AppLocalStoreDb extends RoomDatabase {
    public abstract PersonalInformationDAO personalInformationDAO();
    public abstract CategoryDao categoryDao();
}