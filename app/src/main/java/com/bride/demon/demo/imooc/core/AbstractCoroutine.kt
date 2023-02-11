package com.bride.demon.demo.imooc.core

import com.bride.demon.demo.imooc.*
import com.bride.demon.demo.imooc.scope.CoroutineScope
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.*

abstract class AbstractCoroutine<T>(context: CoroutineContext)
    : Job, Continuation<T>, CoroutineScope {

    protected val state = AtomicReference<CoroutineState>()

    override val context: CoroutineContext = context + this

    override val scopeContext: CoroutineContext
        get() = context

    protected val parentJob = context[Job]

    private var parentCancelDisposable: Disposable? = null

    init {
        state.set(CoroutineState.InComplete())
        // 父协程取消后，取消子协程。
        // 当前协程创建时，添加监听。当前协程取消或完成，移除监听。
        parentCancelDisposable = parentJob?.invokeOnCancel {
            cancel()
        }
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

        parentCancelDisposable?.dispose()
    }

    private fun tryHandleException(e: Throwable): Boolean {
        return when(e) {
            is CancellationException -> false// 有协程被取消，并且有挂起函数响应了
            else -> {
                (parentJob as? AbstractCoroutine<*>)?.handleChildException(e)?.takeIf { it }
                    ?: handleJobException(e)
            }
        }
    }

    protected open fun handleJobException(e: Throwable): Boolean {
        // 此处不处理，需要子类来覆写
        return false
    }

    protected open fun handleChildException(e: Throwable): Boolean {
        cancel()
        return tryHandleException(e)
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
            is CoroutineState.Complete<*> -> {
                // 响应当前协程的取消事件
                val currentCallingJobState = coroutineContext[Job]?.isActive?:return
                if (!currentCallingJobState) {
                    throw CancellationException("Coroutine is cancelled.")
                }
                return
            }
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

        parentCancelDisposable?.dispose()
    }

    override fun toString(): String {
        return "${context[CoroutineName]?.name}"
    }
}