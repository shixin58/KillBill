package com.bride.thirdparty;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bride.baselib.PreferenceUtils;
import com.bride.baselib.ResUtils;

/**
 * <p>Created by shixin on 2018/9/20.
 */
public class ThirdPartyApplication extends Application {
    private static ThirdPartyApplication application;
    private RequestQueue mRequestQueue;

    public static ThirdPartyApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        ResUtils.setContext(this);

        PreferenceUtils.initialize(this, "thirdparty_prefs");

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
