package tmrapps.getinshapeapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by nathan on 02/02/2018.
 */

public class GetInShapeApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


    public static Context getMyContext(){
        return context;
    }

}
