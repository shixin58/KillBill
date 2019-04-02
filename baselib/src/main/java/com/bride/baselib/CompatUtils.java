package com.bride.baselib;

import android.os.Build;
import android.os.StrictMode;
import android.os.strictmode.Violation;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>Created by shixin on 2019/4/2.
 */
public class CompatUtils {
    private static final String TAG = CompatUtils.class.getSimpleName();

    public static void detectNonSdkApi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ExecutorService executorService = Executors.newFixedThreadPool(8);
            StrictMode.VmPolicy vmPolicy = new StrictMode.VmPolicy.Builder()
                    .detectNonSdkApiUsage()
                    .penaltyListener(executorService, new StrictMode.OnVmViolationListener() {
                        @Override
                        public void onVmViolation(Violation v) {
                            Log.w(TAG, "detectNonSdkApiUsage - onVmViolation", v);
                        }
                    })
                    /*.penaltyLog()*/
                    .build();
            StrictMode.setVmPolicy(vmPolicy);
        }
    }
}
