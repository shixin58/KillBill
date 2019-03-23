package com.bride.demon.callback;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.util.Log;
import android.view.View;

/**
 * 由FragmentActivity#getSupportFragmentManager()注册注销，监控一个Activity下所有Fragment的生命周期
 * <p>Created by shixin on 2018/10/1.
 */
public class MyFragmentLifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks {
    private static final String TAG = MyFragmentLifecycleCallbacks.class.getSimpleName();

    @Override
    public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
        super.onFragmentAttached(fm, f, context);
        Log.i(TAG, "onFragmentAttached - "+f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
        Log.i(TAG, "onFragmentCreated - "+f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentViewCreated(FragmentManager fm, Fragment f, View v, Bundle savedInstanceState) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState);
        Log.i(TAG, "onFragmentViewCreated - "+f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentActivityCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
        super.onFragmentActivityCreated(fm, f, savedInstanceState);
        Log.i(TAG, "onFragmentActivityCreated - "+f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentStarted(FragmentManager fm, Fragment f) {
        super.onFragmentStarted(fm, f);
        Log.i(TAG, "onFragmentStarted - "+f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentResumed(FragmentManager fm, Fragment f) {
        super.onFragmentResumed(fm, f);
        Log.i(TAG, "onFragmentResumed - "+f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentPaused(FragmentManager fm, Fragment f) {
        super.onFragmentPaused(fm, f);
        Log.i(TAG, "onFragmentPaused - "+f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentStopped(FragmentManager fm, Fragment f) {
        super.onFragmentStopped(fm, f);
        Log.i(TAG, "onFragmentStopped - "+f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentViewDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentViewDestroyed(fm, f);
        Log.i(TAG, "onFragmentViewDestroyed - "+f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
        super.onFragmentDestroyed(fm, f);
        Log.i(TAG, "onFragmentDestroyed - "+f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentDetached(FragmentManager fm, Fragment f) {
        super.onFragmentDetached(fm, f);
        Log.i(TAG, "onFragmentDetached - "+f.getClass().getSimpleName());
    }
}
