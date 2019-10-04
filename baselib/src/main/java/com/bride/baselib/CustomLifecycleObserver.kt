package com.bride.baselib

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * 由FragmentActivity#getLifecycle()注册注销，监控Activity生命周期
 *
 * Created by shixin on 2018/10/1.
 */
object CustomLifecycleObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(lifecycleOwner: LifecycleOwner) {
        LogUtils.v(LogUtils.TAG_LIFECYCLE_OBSERVER, "event: ON_CREATE; state: ${lifecycleOwner.lifecycle.currentState.name}; ${lifecycleOwner.javaClass.simpleName}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(lifecycleOwner: LifecycleOwner) {
        LogUtils.v(LogUtils.TAG_LIFECYCLE_OBSERVER, "event: ON_START; state: ${lifecycleOwner.lifecycle.currentState.name}; ${lifecycleOwner.javaClass.simpleName}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(lifecycleOwner: LifecycleOwner) {
        LogUtils.v(LogUtils.TAG_LIFECYCLE_OBSERVER, "event: ON_RESUME; state: ${lifecycleOwner.lifecycle.currentState.name}; ${lifecycleOwner.javaClass.simpleName}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause(lifecycleOwner: LifecycleOwner) {
        LogUtils.v(LogUtils.TAG_LIFECYCLE_OBSERVER, "event: ON_PAUSE; state: ${lifecycleOwner.lifecycle.currentState.name}; ${lifecycleOwner.javaClass.simpleName}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(lifecycleOwner: LifecycleOwner) {
        LogUtils.v(LogUtils.TAG_LIFECYCLE_OBSERVER, "event: ON_STOP; state: ${lifecycleOwner.lifecycle.currentState.name}; ${lifecycleOwner.javaClass.simpleName}")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(lifecycleOwner: LifecycleOwner) {
        LogUtils.v(LogUtils.TAG_LIFECYCLE_OBSERVER, "event: ON_DESTROY; state: ${lifecycleOwner.lifecycle.currentState.name}; ${lifecycleOwner.javaClass.simpleName}")
    }
}
