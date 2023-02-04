package com.bride.baselib.dispatcher

object Dispatchers {
    val Default by lazy { DispatcherContext(DefaultDispatcher) }

    val Main by lazy { DispatcherContext(HandlerDispatcher) }
}