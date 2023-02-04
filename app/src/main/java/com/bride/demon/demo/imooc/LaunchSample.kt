package com.bride.demon.demo.imooc

import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun main() {
    // 仿官方框架实现launch
    val job = launch {
        log("1")
        val result = helloWorld()
        // 未设置调度器，所以未进行线程切换。在哪resume，返回后在哪个线程执行。
        log("2 $result")
        // 仿官方框架实现delay
        delay(1000L)
        log("3")
    }
    log(job.isActive)
    job.join()
}

suspend fun helloWorld() = suspendCoroutine<Int> { continuation ->
    // 切线程
    thread(isDaemon = true) {
        Thread.sleep(1000L)
        // 恢复
        continuation.resume(10086)
    }
}