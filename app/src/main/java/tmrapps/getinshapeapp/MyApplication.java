package tmrapps.getinshapeapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by tamir on 2/2/2018.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
