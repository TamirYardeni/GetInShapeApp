package tmrapps.getinshapeapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by nathan on 02/02/2018.
 * This class run first and we use it to save things that are shared to all the app
 */
public class GetInShapeApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        // Save the context of the app
        context = getApplicationContext();
    }

    // Get the context of the application
    public static Context getMyContext(){
        return context;
    }

}
