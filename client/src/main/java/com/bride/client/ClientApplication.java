package com.bride.client;

import android.app.Application;

import com.bride.baselib.PreferenceUtils;
import com.bride.baselib.ResUtils;
import com.squareup.leakcanary.LeakCanary;

/**
 * <p>Created by shixin on 2018/10/30.
 */
public class ClientApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        ResUtils.setContext(this);

        PreferenceUtils.initialize(this, "client_prefs");
    }
}
