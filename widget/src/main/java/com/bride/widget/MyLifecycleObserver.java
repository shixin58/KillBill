package com.bride.widget;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * 由FragmentActivity#getLifecycle()注册注销，监控Activity生命周期
 * <p>Created by shixin on 2018/10/1.
 */
public class MyLifecycleObserver implements LifecycleObserver {
    private static final String TAG = MyLifecycleObserver.class.getSimpleName();

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(LifecycleOwner lifecycleOwner) {
        Log.v(TAG, lifecycleOwner.getLifecycle().getCurrentState().name());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart(LifecycleOwner lifecycleOwner) {
        Log.v(TAG, lifecycleOwner.getLifecycle().getCurrentState().name());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(LifecycleOwner lifecycleOwner) {
        Log.v(TAG, lifecycleOwner.getLifecycle().getCurrentState().name());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(LifecycleOwner lifecycleOwner) {
        Log.v(TAG, lifecycleOwner.getLifecycle().getCurrentState().name());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(LifecycleOwner lifecycleOwner) {
        Log.v(TAG, lifecycleOwner.getLifecycle().getCurrentState().name());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner lifecycleOwner) {
        Log.v(TAG, lifecycleOwner.getLifecycle().getCurrentState().name());
    }
}
