package com.bride.demon.callback;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.View;

import timber.log.Timber;

/**
 * 由FragmentActivity#getSupportFragmentManager()注册注销，监控一个Activity下所有Fragment的生命周期
 * <p>Created by shixin on 2018/10/1.
 */
public class MyFragmentLifecycleCallbacks extends FragmentManager.FragmentLifecycleCallbacks {
    @Override
    public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        super.onFragmentAttached(fm, f, context);
        Timber.v("onFragmentAttached - %s", f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, Bundle savedInstanceState) {
        super.onFragmentCreated(fm, f, savedInstanceState);
        Timber.v("onFragmentCreated - %s", f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, Bundle savedInstanceState) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState);
        Timber.v("onFragmentViewCreated - %s", f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentStarted(fm, f);
        Timber.v("onFragmentStarted - %s", f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentResumed(fm, f);
        Timber.v("onFragmentResumed - %s", f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentPaused(fm, f);
        Timber.v("onFragmentPaused - %s", f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentStopped(fm, f);
        Timber.v("onFragmentStopped - %s", f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentViewDestroyed(fm, f);
        Timber.v("onFragmentViewDestroyed - %s", f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentDestroyed(fm, f);
        Timber.v("onFragmentDestroyed - %s", f.getClass().getSimpleName());
    }

    @Override
    public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentDetached(fm, f);
        Timber.v("onFragmentDetached - %s", f.getClass().getSimpleName());
    }
}
