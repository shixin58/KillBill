package com.bride.baselib.dispatcher

import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor

interface IDispatcher {
    fun dispatch(block: () -> Unit)
}

open class DispatcherContext(private val dispatcher: IDispatcher = DefaultDispatcher)
    : AbstractCoroutineContextElement(ContinuationInterceptor), ContinuationInterceptor {
    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T>
        = DispatchedContinuation(continuation, dispatcher)
}

private class DispatchedContinuation<T>(val delegate: Continuation<T>, val dispatcher: IDispatcher) : Continuation<T> {
    override val context = delegate.context

    override fun resumeWith(result: Result<T>) {
        dispatcher.dispatch {
            delegate.resumeWith(result)
        }
    }
}