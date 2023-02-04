package com.bride.demon.demo.imooc

import com.bride.baselib.dispatcher.Dispatchers
import com.bride.demon.demo.imooc.core.StandardCoroutine
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

private val coroutineIndex = AtomicInteger(0)

fun launch(context: CoroutineContext = EmptyCoroutineContext, block: suspend () -> Unit): Job {
    // 将线程调度器设置给Completion的context
    val completion = StandardCoroutine(newCoroutineContext(context))
    block.startCoroutine(completion)
    return completion
}

fun newCoroutineContext(context: CoroutineContext): CoroutineContext {
    val combined = context + CoroutineName("@coroutine#${coroutineIndex.getAndIncrement()}")
    return if (combined !== Dispatchers.Default && combined[ContinuationInterceptor] == null) combined + Dispatchers.Default else combined
}