package com.yiche.example.newestversiondemo;

import android.app.Application;

import com.github.moduth.blockcanary.BlockCanary;

/**
 * <p>Created by shixin on 2017/12/15.
 */
public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Do it on main process
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }
}
