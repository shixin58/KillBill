package com.bride.baselib.dispatcher

import android.os.Handler
import android.os.Looper

/**
 * 相当于Dispatchers.Main
 */
object HandlerDispatcher : IDispatcher {
    private val handler = Handler(Looper.getMainLooper())

    override fun dispatch(block: () -> Unit) {
        handler.post(block)
    }
}