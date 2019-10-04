package com.bride.baselib

import android.util.Log

/**
 * <p>Created by shixin on 2019-10-04.
 */
object LogUtils {
    const val TAG_LIFECYCLE_ACTIVITY = "lifecycle_activity"
    const val TAG_LIFECYCLE_APP = "lifecycle_app"
    const val TAG_LIFECYCLE_OBSERVER = "lifecycle_observer"

    fun v(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Log.v(tag, msg)
    }

    fun v(tag: String, msg: String, tr: Throwable) {
        if (BuildConfig.DEBUG)
            Log.v(tag, msg, tr)
    }

    fun d(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Log.d(tag, msg)
    }

    fun d(tag: String, msg: String, tr: Throwable) {
        if (BuildConfig.DEBUG)
            Log.d(tag, msg, tr)
    }

    fun i(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Log.i(tag, msg)
    }

    fun i(tag: String, msg: String, tr: Throwable) {
        if (BuildConfig.DEBUG)
            Log.i(tag, msg, tr)
    }

    fun w(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Log.w(tag, msg)
    }

    fun w(tag: String, msg: String, tr: Throwable) {
        if (BuildConfig.DEBUG)
            Log.w(tag, msg, tr)
    }

    fun e(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Log.e(tag, msg)
    }

    fun e(tag: String, msg: String, tr: Throwable) {
        if (BuildConfig.DEBUG)
            Log.e(tag, msg, tr)
    }
}