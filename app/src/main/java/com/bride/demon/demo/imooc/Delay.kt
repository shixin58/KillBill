package com.bride.demon.demo.imooc

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private val executor = Executors.newScheduledThreadPool(1) { runnable ->
    // 主流程结束正常退出
    Thread(runnable, "Delay-Scheduler").apply { isDaemon = true }
}

suspend fun delay(time: Long, unit: TimeUnit = TimeUnit.MILLISECONDS) = suspendCoroutine<Unit> { continuation ->
    executor.schedule({ continuation.resume(Unit) }, time, unit)
}