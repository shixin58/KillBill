package com.bride.demon.demo.imooc

import com.bride.baselib.dispatcher.DispatcherContext
import com.bride.baselib.dispatcher.Dispatchers
import com.bride.demon.demo.imooc.core.BlockingCoroutine
import com.bride.demon.demo.imooc.core.BlockingQueueDispatcher
import com.bride.demon.demo.imooc.core.DeferredCoroutine
import com.bride.demon.demo.imooc.core.StandardCoroutine
import com.bride.demon.demo.imooc.scope.CoroutineScope
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

private val coroutineIndex = AtomicInteger(0)

fun CoroutineScope.launch(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> Unit): Job {
    // 将线程调度器设置给Completion的context
    val completion = StandardCoroutine(newCoroutineContext(context))
    block.startCoroutine(completion, completion)
    return completion
}

fun <T> CoroutineScope.async(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T): Deferred<T> {
    val completion = DeferredCoroutine<T>(newCoroutineContext(context))
    block.startCoroutine(completion, completion)
    return completion
}

fun CoroutineScope.newCoroutineContext(context: CoroutineContext): CoroutineContext {
    val combined = scopeContext + context + CoroutineName("@coroutine#${coroutineIndex.getAndIncrement()}")
    return if (combined !== Dispatchers.Default && combined[ContinuationInterceptor] == null) combined + Dispatchers.Default else combined
}

/**
 * 事件循环，像Android Looper。异步但不一定切换线程。
 */
fun <T> runBlocking(context: CoroutineContext = EmptyCoroutineContext, block: suspend () -> T): T {
    val eventQueue = BlockingQueueDispatcher()
    val newContext = context + DispatcherContext(eventQueue)
    val completion = BlockingCoroutine<T>(newContext, eventQueue)
    block.startCoroutine(completion)
    return completion.joinBlocking()
}