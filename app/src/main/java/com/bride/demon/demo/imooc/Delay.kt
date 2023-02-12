package com.bride.demon.demo.imooc

import com.bride.demon.demo.imooc.core.suspendCancellableCoroutine
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume

private val executor = Executors.newScheduledThreadPool(1) { runnable ->
    // 主流程结束正常退出
    Thread(runnable, "Delay-Scheduler").apply { isDaemon = true }
}

/**
 * 参考Call#await()
 */
suspend fun delay(time: Long, unit: TimeUnit = TimeUnit.MILLISECONDS) = suspendCancellableCoroutine<Unit> { continuation ->
    val future = executor.schedule({ continuation.resume(Unit) }, time, unit)
    continuation.invokeOnCancel { future.cancel(true) }
}