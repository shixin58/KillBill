package com.bride.client;

import android.app.Application;

import com.bride.baselib.PreferenceUtils;
import com.bride.baselib.ResUtils;

/**
 * <p>Created by shixin on 2018/10/30.
 */
public class ClientApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ResUtils.setContext(this);

        PreferenceUtils.initialize(this, "client_prefs");
    }
}
