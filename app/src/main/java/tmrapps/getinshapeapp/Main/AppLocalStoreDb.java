package tmrapps.getinshapeapp.Main;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import tmrapps.getinshapeapp.Category.Model.Category;
import tmrapps.getinshapeapp.Category.Model.CategoryDao;
import tmrapps.getinshapeapp.Exercise.Model.Exercise;
import tmrapps.getinshapeapp.Exercise.Model.ExerciseDao;
import tmrapps.getinshapeapp.PersonalArea.Model.PersonalInformation;
import tmrapps.getinshapeapp.PersonalArea.Model.PersonalInformationConverter;
import tmrapps.getinshapeapp.PersonalArea.Model.PersonalInformationDAO;

/**
 * Created by nathan on 03/02/2018.
 * Interface that defines the data accesses objects
 */

@Database(entities = {PersonalInformation.class, Category.class, Exercise.class}, version = 8)
@TypeConverters({PersonalInformationConverter.class})
public abstract class AppLocalStoreDb extends RoomDatabase {
    public abstract PersonalInformationDAO personalInformationDAO();
    public abstract CategoryDao categoryDao();
    public abstract ExerciseDao exerciseDao();
}