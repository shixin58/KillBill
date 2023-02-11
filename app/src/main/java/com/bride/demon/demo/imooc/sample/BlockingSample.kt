package com.bride.demon.demo.imooc.sample

import com.bride.baselib.log
import com.bride.demon.demo.imooc.delay
import com.bride.demon.demo.imooc.launch
import com.bride.demon.demo.imooc.runBlocking
import com.bride.demon.demo.imooc.scope.GlobalScope

fun main() = runBlocking {
    log(1)
    val job = GlobalScope.launch {
        log(2)
        delay(100L)
        log(3)
    }
    log(4)
    job.join()
    log(5)
    delay(100L)
    log(6)
}