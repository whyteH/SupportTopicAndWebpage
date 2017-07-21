package tech.laosiji.supporttopicandwebpage;

import android.app.Application;

/**
 * Created by Whyte on 2017/7/21.
 */

public class BaseApplication extends Application {

    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}
