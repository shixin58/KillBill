package com.bride.demon.demo.imooc

import kotlinx.coroutines.delay
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

fun main() {
    // 标准库序列生成器
    val seq = sequence {
        // yield后，懒序列挂起
        yield(1)
        yield(2)
        yield(3)
        yieldAll(listOf(4, 5, 6))
    }

    // for-each loop，Java需要实现Iterable接口，Kotlin仅需定义operator fun iterator()。
    // hasNext()触发resume()
    for (x in seq) {
        println(x)
    }

    suspend {// 类型为suspend () -> Unit
        // 调用suspend fun后，主调用流程挂起1秒
        delay(1000L)
    }.startCoroutine(object : Continuation<Unit> {
        override val context: CoroutineContext = EmptyCoroutineContext

        override fun resumeWith(result: Result<Unit>) {}
    })
}