package com.bride.demon.demo.imooc.core

import com.bride.demon.demo.imooc.Deferred
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.suspendCoroutine

class DeferredCoroutine<T>(context: CoroutineContext) : AbstractCoroutine<T>(context), Deferred<T> {
    override suspend fun await(): T {
        return when(val currentState = state.get()) {
            is CoroutineState.Complete<*> -> (currentState.value as? T)?:throw currentState.exception!!
            is CoroutineState.InComplete -> awaitSuspend()
        }
    }

    private suspend fun awaitSuspend() = suspendCoroutine<T> { continuation ->
        doOnCompleted { result ->
            continuation.resumeWith(result)
        }
    }
}