package com.bride.demon.demo.imooc.core

import com.bride.demon.demo.imooc.*
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

abstract class AbstractCoroutine<T>(context: CoroutineContext) : Job, Continuation<T> {
    protected val state = AtomicReference<CoroutineState>()

    override val context: CoroutineContext = context + this

    init {
        state.set(CoroutineState.InComplete())
    }

    override fun resumeWith(result: Result<T>) {
        val newState = state.updateAndGet { prevState ->
            when(prevState) {
                is CoroutineState.Cancelling,
                is CoroutineState.InComplete -> {
                    CoroutineState.Complete(result.getOrNull(), result.exceptionOrNull()).from(prevState)
                }
                is CoroutineState.Complete<*> -> {
                    throw IllegalStateException("Already completed!")
                }
            }
        }

        (newState as CoroutineState.Complete<T>).exception?.let(::tryHandleException)

        newState.notifyCompletion(result)
        newState.clear()
    }

    private fun tryHandleException(e: Throwable): Boolean {
        return when(e) {
            is CancellationException -> false// 有协程被取消，并且有挂起函数响应了
            else -> {
                handleJobException(e)
            }
        }
    }

    protected open fun handleJobException(e: Throwable): Boolean {
        // 此处不处理，需要子类来覆写
        return false
    }

    override val isActive: Boolean
        get() = state.get() is CoroutineState.InComplete

    override val isCompleted: Boolean
        get() = state.get() is CoroutineState.Complete<*>

    override fun invokeOnCompletion(onComplete: OnComplete): Disposable {
        return doOnCompleted {
            onComplete()
        }
    }

    override fun invokeOnCancel(onCancel: OnCancel): Disposable {
        val disposable = CancellationHandlerDisposable(this, onCancel)
        val newState = state.updateAndGet { prevState ->
            when(prevState) {
                is CoroutineState.Cancelling,
                is CoroutineState.Complete<*> -> prevState
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prevState).with(disposable)
                }
            }
        }
        (newState as? CoroutineState.Cancelling)?.let {
            onCancel()
        }
        return disposable
    }

    override fun remove(disposable: Disposable) {
        state.updateAndGet { prevState ->
            when(prevState) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prevState).without(disposable)
                }
                is CoroutineState.Cancelling -> {
                    CoroutineState.Cancelling().from(prevState).without(disposable)
                }
                is CoroutineState.Complete<*> -> prevState
            }
        }
    }

    override suspend fun join() {
        when(state.get()) {
            is CoroutineState.Cancelling,
            is CoroutineState.InComplete -> joinSuspend()
            is CoroutineState.Complete<*> -> return
        }
    }

    private suspend fun joinSuspend() = suspendCoroutine<Unit> { continuation ->
        doOnCompleted { result ->
            continuation.resume(Unit)
        }
    }

    protected fun doOnCompleted(block: (Result<T>) -> Unit): Disposable {
        val disposable = CompletionHandlerDisposable(this, block)
        val newState = state.updateAndGet { prevState ->
            when(prevState) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prevState).with(disposable)
                }
                is CoroutineState.Cancelling -> {
                    CoroutineState.Cancelling().from(prevState).with(disposable)
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
        val newState = state.updateAndGet { prevState ->
            when(prevState) {
                is CoroutineState.Cancelling,
                is CoroutineState.Complete<*> -> prevState
                is CoroutineState.InComplete -> {
                    CoroutineState.Cancelling().from(prevState)
                }
            }
        }
        if (newState is CoroutineState.Cancelling) {
            newState.notifyCancellation()
        }
    }

    override fun toString(): String {
        return "${context[CoroutineName]?.name}"
    }
}