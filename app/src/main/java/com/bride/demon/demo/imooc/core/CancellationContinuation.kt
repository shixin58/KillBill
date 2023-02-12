package com.bride.demon.demo.imooc.core

import com.bride.demon.demo.imooc.CancellationException
import com.bride.demon.demo.imooc.Job
import com.bride.demon.demo.imooc.OnCancel
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.intrinsics.intercepted
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

class CancellationContinuation<T>(private val continuation: Continuation<T>) : Continuation<T> by continuation {
    private val state = AtomicReference<CancelState>(CancelState.InComplete)

    private val cancelHandlers = CopyOnWriteArrayList<OnCancel>()

    val isCompleted: Boolean
        get() = state.get() is CancelState.Complete<*>

    val isActive: Boolean
        get() = state.get() == CancelState.InComplete

    override fun resumeWith(result: Result<T>) {
        state.updateAndGet { prevState ->
            when(prevState) {
                CancelState.InComplete -> {
                    continuation.resumeWith(result)
                    CancelState.Complete(result.getOrNull(), result.exceptionOrNull())
                }
                CancelState.Cancelled -> {
                    CancellationException("Cancelled.").let {
                        continuation.resumeWith(Result.failure(it))
                        CancelState.Complete(null, it)
                    }
                }
                is CancelState.Complete<*> -> throw IllegalStateException("Already completed.")
            }
        }
    }

    fun invokeOnCancel(onCancel: OnCancel) {
        cancelHandlers += onCancel
    }

    fun cancel() {
        if (!isActive) return
        val parent = continuation.context[Job]?:return
        parent.cancel()
    }

    fun getResult(): Any? {
        installCancelHandler()
        return when(val currentState = state.get()) {
            CancelState.Cancelled -> throw CancellationException("Continuation is cancelled.")
            is CancelState.Complete<*> -> {
                (currentState as CancelState.Complete<T>).let {
                    it.exception?.let { e -> throw e }?:it.value
                }
            }
            CancelState.InComplete -> COROUTINE_SUSPENDED
        }
    }

    private fun installCancelHandler() {
        // 为响应取消，先监听取消状态
        if (!isActive) return
        val parent = continuation.context[Job]?:return
        parent.invokeOnCancel {
            doCancel()
        }
    }

    private fun doCancel() {
        state.updateAndGet { prevState ->
            when(prevState) {
                CancelState.Cancelled,
                is CancelState.Complete<*> -> prevState
                CancelState.InComplete -> CancelState.Cancelled
            }
        }
        cancelHandlers.forEach(OnCancel::invoke)
        cancelHandlers.clear()
    }
}

/**
 * suspendCancellableCoroutine函数实现参考了suspendCoroutine内部实现。suspendCoroutine用来拿到suspend fun的Continuation。
 */
suspend inline fun <T> suspendCancellableCoroutine(
    crossinline block: (CancellationContinuation<T>) -> Unit
): T = suspendCoroutineUninterceptedOrReturn { c: Continuation<T> ->
    val cancellationContinuation = CancellationContinuation(c.intercepted())
    block(cancellationContinuation)
    cancellationContinuation.getResult()
}

sealed class CancelState {
    override fun toString(): String {
        return "CancelState.${javaClass.simpleName}"
    }

    object InComplete : CancelState()
    class Complete<T>(val value: T? = null, val exception: Throwable? = null) : CancelState()
    object Cancelled : CancelState()
}