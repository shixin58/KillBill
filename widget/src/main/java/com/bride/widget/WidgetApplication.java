package com.bride.widget;

import android.app.Application;
import android.content.Context;

import com.bride.baselib.CompatUtils;
import com.bride.baselib.PreferenceUtils;
import com.bride.baselib.ResUtils;

/**
 * <p>Created by shixin on 2018/10/30.
 */
public class WidgetApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        CompatUtils.detectThread();
        CompatUtils.detectVm();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ResUtils.setContext(this);

        PreferenceUtils.initialize(this, "widget_prefs");
    }
}
