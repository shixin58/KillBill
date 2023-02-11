package com.bride.demon.demo.imooc.core

import com.bride.demon.demo.imooc.CancellationException
import com.bride.demon.demo.imooc.Deferred
import com.bride.demon.demo.imooc.Job
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine

class DeferredCoroutine<T>(context: CoroutineContext) : AbstractCoroutine<T>(context), Deferred<T> {
    override suspend fun await(): T {
        return when(val currentState = state.get()) {
            is CoroutineState.Cancelling,
            is CoroutineState.InComplete -> awaitSuspend()
            is CoroutineState.Complete<*> -> {
                coroutineContext[Job]?.isActive?.takeIf { !it }?.let {
                    // CoroutineState.Cancelling
                    throw CancellationException("Coroutine is cancelled.")
                }
                (currentState.value as? T)?:throw currentState.exception!!
            }
        }
    }

    private suspend fun awaitSuspend() = suspendCoroutine<T> { continuation ->
        doOnCompleted { result ->
            continuation.resumeWith(result)
        }
    }
}