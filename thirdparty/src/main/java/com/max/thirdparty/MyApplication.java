package com.max.thirdparty;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * <p>Created by shixin on 2018/9/20.
 */
public class MyApplication extends Application {
    private static MyApplication application;
    private RequestQueue mRequestQueue;

    public static MyApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        init();
    }

    private void init() {
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
