package com.bride.demon.demo.imooc

import com.bride.demon.demo.imooc.core.Disposable
import kotlin.coroutines.CoroutineContext

typealias OnComplete = () -> Unit

typealias CancellationException = java.util.concurrent.CancellationException

interface Job : CoroutineContext.Element {
    companion object Key : CoroutineContext.Key<Job>

    override val key: CoroutineContext.Key<*>
        get() = Job

    /**
     * 类似Thread#isAlive()
     */
    val isActive: Boolean

    val isCompleted: Boolean

    fun invokeOnCompletion(onComplete: OnComplete): Disposable

    fun invokeOnCancel(onCancel: OnCancel): Disposable

    fun remove(disposable: Disposable)

    /**
     * 类似Thread#join()
     */
    suspend fun join()

    /**
     * 类似Thread#interrupt()
     */
    fun cancel()
}