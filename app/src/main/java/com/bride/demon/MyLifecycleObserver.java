package com.bride.demon;

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
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void start(LifecycleOwner lifecycleOwner) {
        Log.i("start", ""+lifecycleOwner.getLifecycle().getCurrentState());
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stop(LifecycleOwner lifecycleOwner) {
        Log.i("stop", ""+lifecycleOwner.getLifecycle().getCurrentState());
    }
}
