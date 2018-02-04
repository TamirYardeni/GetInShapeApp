package tmrapps.getinshapeapp.Main;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import tmrapps.getinshapeapp.Category.Model.Category;
import tmrapps.getinshapeapp.Category.Model.CategoryDao;
import tmrapps.getinshapeapp.GetInShapeApp;
import tmrapps.getinshapeapp.PersonalArea.Model.PersonalInformationDAO;
import tmrapps.getinshapeapp.PersonalArea.Model.PersonalInformation;

/**
 * Created by tamir on 1/19/2018.
 */

public class AppLocalStore{
    static public AppLocalStoreDb db = Room.databaseBuilder(GetInShapeApp.getMyContext(),
            AppLocalStoreDb.class,
            "get-in-shape-db").build();
}
