package com.bride.demon.demo.imooc.core

import com.bride.demon.demo.imooc.Job
import com.bride.demon.demo.imooc.OnCancel
import com.bride.demon.demo.imooc.OnComplete
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

abstract class AbstractCoroutine<T>(override val context: CoroutineContext) : Job, Continuation<T> {
    protected val state = AtomicReference<CoroutineState>()

    init {
        state.set(CoroutineState.InComplete())
    }

    override fun resumeWith(result: Result<T>) {
        val newState = state.updateAndGet { prevState ->
            when(prevState) {
                is CoroutineState.InComplete -> {
                    CoroutineState.Complete(result.getOrNull(), result.exceptionOrNull()).from(prevState)
                }
                is CoroutineState.Complete<*> -> {
                    throw IllegalStateException("Already completed!")
                }
            }
        }
        newState.notifyCompletion(result)
        newState.clear()
    }

    override val isActive: Boolean
        get() = state.get() is CoroutineState.InComplete

    override val isCompleted: Boolean
        get() = TODO("Not yet implemented")

    override fun invokeOnCompletion(onComplete: OnComplete): Disposable {
        return doOnCompleted {
            onComplete()
        }
    }

    override fun invokeOnCancel(onCancel: OnCancel): Disposable {
        TODO("Not yet implemented")
    }

    override fun remove(disposable: Disposable) {
        state.updateAndGet { prevState ->
            when(prevState) {
                is CoroutineState.Complete<*> -> prevState
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prevState).without(disposable)
                }
            }
        }
    }

    override suspend fun join() {
        when(state.get()) {
            is CoroutineState.Complete<*> -> return
            is CoroutineState.InComplete -> joinSuspend()
        }
    }

    private suspend fun joinSuspend() = suspendCoroutine<Unit> { continuation ->
        doOnCompleted { result ->
            continuation.resume(Unit)
        }
    }

    private fun doOnCompleted(block: (Result<T>) -> Unit): Disposable {
        val disposable = CompletionHandlerDisposable(this, block)
        val newState = state.updateAndGet { prevState ->
            when(prevState) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete()
                        .from(prevState)
                        .with(disposable)
                }
                is CoroutineState.Complete<*> -> {
                    // 已经结束，什么都不干
                    prevState
                }
            }
        }
        (newState as? CoroutineState.Complete<T>)?.let {
            block(
                when {
                    it.value != null -> Result.success(it.value)
                    it.exception != null -> Result.failure(it.exception)
                    else -> throw IllegalStateException("Won't happen!")
                }
            )
        }
        return disposable
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }
}