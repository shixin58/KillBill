package com.bride.demon.demo

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread

/**
 * <p>Created by shixin on 2019-04-20.
 */
fun main() {
//    example()
//    example2()
    example3()
}

fun example(): Unit {
    println("Start")

    GlobalScope.launch {
        delay(1000)
        println("Hello")
    }

    Thread.sleep(2000)
    println("Stop")

    runBlocking {
        delay(1000)
        println("World")
    }

    Thread.sleep(2000)
    println("End")
}

fun example2(): Unit {
    val c = AtomicLong()

    for (i in 1..1_000_000L)
        thread(start = true) {
            c.addAndGet(i)
        }
    println(c.get())
}

fun example3(): Unit {
    val deferred = (1..1_000_000).map { n ->
        GlobalScope.async {
            workload(n)
        }
    }
    runBlocking {
        val sum = deferred.sumBy {
            it.await()
        }
        println("Sum: $sum")
    }
}

suspend fun workload(n: Int): Int {
    delay(1000)
    return n
}