package com.dobbby.kdietitian;

import android.app.Application;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Dobbby on 2017/6/25.
 */
public class KDApplication extends Application {
    private static KDApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        initLeakCanary();
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        LeakCanary.install(this);
    }

    public static KDApplication getInstance() {
        return instance;
    }
}
