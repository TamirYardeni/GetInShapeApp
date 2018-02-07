package tmrapps.getinshapeapp.Main;

import android.arch.persistence.room.Room;

import tmrapps.getinshapeapp.GetInShapeApp;


/**
 * Created by tamir on 1/19/2018.
 * Connect the app to the local db with Room library
 */
public class AppLocalStore{
    static public AppLocalStoreDb db = Room.databaseBuilder(GetInShapeApp.getMyContext(),
            AppLocalStoreDb.class,
            "get-in-shape-db").fallbackToDestructiveMigration().build();
}
