package com.yiche.example.newestversiondemo;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;

/**
 * <p>Created by shixin on 2017/12/15.
 */
public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initLeakCanary();

        initBlockCanary();
    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    private void initBlockCanary() {
        // Do it on main process
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }
}
