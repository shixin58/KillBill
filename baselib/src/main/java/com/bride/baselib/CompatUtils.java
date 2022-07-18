package com.bride.baselib;

import android.os.Build;
import android.os.StrictMode;
import android.os.strictmode.Violation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 演示StrictMode用法发现异常
 * <p>Created by shixin on 2019/4/2.
 */
public class CompatUtils {
    private static final String TAG = CompatUtils.class.getSimpleName();

    public static void detectVm() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();

        builder.detectActivityLeaks()
                .detectFileUriExposure()
                .detectLeakedClosableObjects()
                .detectLeakedRegistrationObjects()
                .detectLeakedSqlLiteObjects();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.detectCleartextNetwork();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.detectContentUriWithoutPermission()
                    .detectUntaggedSockets();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            builder.detectNonSdkApiUsage();
            ExecutorService executorService = Executors.newFixedThreadPool(8);
            builder.penaltyListener(executorService, new StrictMode.OnVmViolationListener(){
                @Override
                public void onVmViolation(Violation v) {
                }
            });
        }
        builder.penaltyLog();

        StrictMode.VmPolicy vmPolicy = builder.build();
        StrictMode.setVmPolicy(vmPolicy);
    }

    public static void detectThread() {
        StrictMode.ThreadPolicy.Builder builder = new StrictMode.ThreadPolicy.Builder();

        builder.detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .detectCustomSlowCalls();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            builder.detectResourceMismatches();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.detectUnbufferedIo();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ExecutorService executorService = Executors.newFixedThreadPool(8);
            builder.penaltyListener(executorService, new StrictMode.OnThreadViolationListener() {
                @Override
                public void onThreadViolation(Violation v) {
                }
            });
        }
        builder.penaltyLog();

        StrictMode.ThreadPolicy threadPolicy = builder.build();
        StrictMode.setThreadPolicy(threadPolicy);
    }
}
