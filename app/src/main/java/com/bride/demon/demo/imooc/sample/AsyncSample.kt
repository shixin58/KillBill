package com.bride.demon.demo.imooc.sample

import com.bride.baselib.log
import com.bride.demon.demo.imooc.async
import com.bride.demon.demo.imooc.delay
import com.bride.demon.demo.imooc.scope.GlobalScope

suspend fun main() {
    log(1)
    val deferred = GlobalScope.async<Int> {
        log(2)
        delay(1000L)
        log(3)
        "Smile"
        throw ArithmeticException("Divided by zero")
    }
    log(4)
    try {
        val result = deferred.await()
        log("5 $result")
    } catch (e: Throwable) {
        log("6 ${e.message}")
    }
}