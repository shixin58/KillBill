package com.bride.baselib.dispatcher

import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

/**
 * 相当于默认调度器Dispatchers.Default
 */
object DefaultDispatcher : IDispatcher {
    // 对线程分个类
    private val threadGroup = ThreadGroup("DefaultDispatcher")

    // 用AtomicInteger辅助生成唯一线程名
    private val threadIndex = AtomicInteger(0)

    private val executor = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors()) { runnable ->
        Thread(threadGroup, runnable, "${threadGroup.name}-worker-${threadIndex.getAndIncrement()}").apply {
            isDaemon = true
        }
    }

    override fun dispatch(block: () -> Unit) {
        executor.submit(block)
    }
}