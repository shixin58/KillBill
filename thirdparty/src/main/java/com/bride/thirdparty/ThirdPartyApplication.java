package com.bride.thirdparty;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.os.strictmode.Violation;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bride.baselib.PreferenceUtils;
import com.bride.baselib.ResUtils;
import com.facebook.stetho.Stetho;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        detectNonSdkApi();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        application = this;

        ResUtils.setContext(this);

        PreferenceUtils.initialize(this, "thirdparty_prefs");

        mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    private void detectNonSdkApi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ExecutorService executorService = Executors.newFixedThreadPool(8);
            StrictMode.VmPolicy vmPolicy = new StrictMode.VmPolicy.Builder()
                    .detectNonSdkApiUsage()
                    .penaltyListener(executorService, new StrictMode.OnVmViolationListener() {
                        @Override
                        public void onVmViolation(Violation v) {
                            Log.i("detectNonSdkApiUsage", "onVmViolation", v);
                        }
                    })
                    /*.penaltyLog()*/
                    .build();
            StrictMode.setVmPolicy(vmPolicy);
        }
    }
}
