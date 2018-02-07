package tmrapps.getinshapeapp.Main;

import android.arch.persistence.room.Room;

import tmrapps.getinshapeapp.GetInShapeApp;


/**
 * Created by tamir on 1/19/2018.
 */

public class AppLocalStore{
    static public AppLocalStoreDb db = Room.databaseBuilder(GetInShapeApp.getMyContext(),
            AppLocalStoreDb.class,
            "get-in-shape-db").fallbackToDestructiveMigration().build();
}
