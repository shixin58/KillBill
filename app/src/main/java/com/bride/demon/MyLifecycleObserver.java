package com.bride.demon;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;

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
