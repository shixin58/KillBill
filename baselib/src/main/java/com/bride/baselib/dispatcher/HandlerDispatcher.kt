package com.bride.baselib.dispatcher

import android.os.Handler
import android.os.Looper

object HandlerDispatcher : IDispatcher {
    private val handler = Handler(Looper.getMainLooper())

    override fun dispatch(block: () -> Unit) {
        handler.post(block)
    }
}