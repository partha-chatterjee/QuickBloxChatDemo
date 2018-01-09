package partha.qbchatdemo.singleton;

import android.app.Application;
import android.content.Context;

/**
 * singleton application context
 * TODO: Created by Partha Chatterjee on 09-08-2016
 */
public class AppContext extends Application {
    private static AppContext sInstance = null;

    public static AppContext getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
