package com.bride.baselib

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * <p>Created by shixin on 2019-10-04.
 */
object ProcessLifecycleObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(lifecycleOwner: LifecycleOwner){
        LogUtils.v(LogUtils.TAG_LIFECYCLE_APP, "onCreate; ${lifecycleOwner.javaClass.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(lifecycleOwner: LifecycleOwner){
        LogUtils.v(LogUtils.TAG_LIFECYCLE_APP, "onStart; ${lifecycleOwner.javaClass.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(lifecycleOwner: LifecycleOwner){
        LogUtils.v(LogUtils.TAG_LIFECYCLE_APP, "onResume; ${lifecycleOwner.javaClass.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(lifecycleOwner: LifecycleOwner){
        LogUtils.v(LogUtils.TAG_LIFECYCLE_APP, "onPause; ${lifecycleOwner.javaClass.name}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(lifecycleOwner: LifecycleOwner){
        LogUtils.v(LogUtils.TAG_LIFECYCLE_APP, "onStop; ${lifecycleOwner.javaClass.name}")
    }
}