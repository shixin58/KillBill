package com.bride.baselib

import androidx.lifecycle.*

/**
 * 由FragmentActivity#getLifecycle()注册注销，监控Activity生命周期
 *
 * Created by shixin on 2018/10/1.
 */
object CustomLifecycleObserver : DefaultLifecycleObserver {

    override fun onCreate(lifecycleOwner: LifecycleOwner) {
        LogUtils.v(LogUtils.TAG_LIFECYCLE_OBSERVER, "event: ON_CREATE; state: ${lifecycleOwner.lifecycle.currentState.name}; ${lifecycleOwner.javaClass.simpleName}")
    }

    override fun onStart(lifecycleOwner: LifecycleOwner) {
        LogUtils.v(LogUtils.TAG_LIFECYCLE_OBSERVER, "event: ON_START; state: ${lifecycleOwner.lifecycle.currentState.name}; ${lifecycleOwner.javaClass.simpleName}")
    }

    override fun onResume(lifecycleOwner: LifecycleOwner) {
        LogUtils.v(LogUtils.TAG_LIFECYCLE_OBSERVER, "event: ON_RESUME; state: ${lifecycleOwner.lifecycle.currentState.name}; ${lifecycleOwner.javaClass.simpleName}")
    }

    override fun onPause(lifecycleOwner: LifecycleOwner) {
        LogUtils.v(LogUtils.TAG_LIFECYCLE_OBSERVER, "event: ON_PAUSE; state: ${lifecycleOwner.lifecycle.currentState.name}; ${lifecycleOwner.javaClass.simpleName}")
    }

    override fun onStop(lifecycleOwner: LifecycleOwner) {
        LogUtils.v(LogUtils.TAG_LIFECYCLE_OBSERVER, "event: ON_STOP; state: ${lifecycleOwner.lifecycle.currentState.name}; ${lifecycleOwner.javaClass.simpleName}")
    }

    override fun onDestroy(lifecycleOwner: LifecycleOwner) {
        LogUtils.v(LogUtils.TAG_LIFECYCLE_OBSERVER, "event: ON_DESTROY; state: ${lifecycleOwner.lifecycle.currentState.name}; ${lifecycleOwner.javaClass.simpleName}")
    }
}
