package partha.qbchatdemo.chat.utils;

import android.content.Context;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.quickblox.auth.session.QBSettings;

/**
 * Created by Partha Chatterjee on 10-11-2017.
 */

public class TrackApplication extends MultiDexApplication {
    private static TrackApplication sInstance = null;

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
        sInstance = this;

        QBSettings.getInstance().init(this,
                Constant.APP_ID,
                Constant.AUTH_KEY,
                Constant.AUTH_SECRET);

        QBSettings.getInstance().setAccountKey(Constant.ACC_KEY);

        //Allowing Strict mode policy for Nougat support
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }
}
