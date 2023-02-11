package com.bride.demon.demo.imooc.sample

import com.bride.baselib.log
import com.bride.demon.demo.imooc.Job
import com.bride.demon.demo.imooc.core.CoroutineExceptionHandler
import com.bride.demon.demo.imooc.delay
import com.bride.demon.demo.imooc.launch
import com.bride.demon.demo.imooc.scope.GlobalScope
import com.bride.demon.demo.imooc.scope.supervisorScope
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun main() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        log("${coroutineContext[Job]} $throwable")
    }

    // 仿官方框架实现launch
    val job = GlobalScope.launch(exceptionHandler) {
        log("1")
        delay(1000L)
        supervisorScope {
            log("2")
            val job2 = launch {
                throw ArithmeticException("Divided by zero")
            }
            log(3)
            job2.join()
            log(4)
        }
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