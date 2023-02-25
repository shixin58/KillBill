package com.bride.baselib

import timber.log.Timber

/**
 * <p>Created by shixin on 2019-10-04.
 */
object LogUtils {
    const val TAG_LIFECYCLE_ACTIVITY = "lifecycle_activity"

    fun v(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Timber.tag(tag).v(msg)
    }

    fun v(tag: String, msg: String, tr: Throwable) {
        if (BuildConfig.DEBUG)
            Timber.tag(tag).v(tr, msg)
    }

    fun d(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Timber.tag(tag).d(msg)
    }

    fun d(tag: String, msg: String, tr: Throwable) {
        if (BuildConfig.DEBUG)
            Timber.tag(tag).d(tr, msg)
    }

    fun i(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Timber.tag(tag).i(msg)
    }

    fun i(tag: String, msg: String, tr: Throwable) {
        if (BuildConfig.DEBUG)
            Timber.tag(tag).i(tr, msg)
    }

    fun w(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Timber.tag(tag).w(msg)
    }

    fun w(tag: String, msg: String, tr: Throwable) {
        if (BuildConfig.DEBUG)
            Timber.tag(tag).w(tr, msg)
    }

    fun e(tag: String, msg: String) {
        if (BuildConfig.DEBUG)
            Timber.tag(tag).e(msg)
    }

    fun e(tag: String, msg: String, tr: Throwable) {
        if (BuildConfig.DEBUG)
            Timber.tag(tag).e(tr, msg)
    }
}