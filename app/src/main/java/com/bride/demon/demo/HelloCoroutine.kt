package com.bride.demon.demo

import kotlinx.coroutines.*
import java.text.DateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import kotlin.concurrent.thread

/**
 * <p>Created by shixin on 2019-04-20.
 */
/*fun main() {
    example()
//    example2()
//    example3()
}*/

fun main() = runBlocking<Unit> {
    /*GlobalScope.launch {
        delay(1000L)
        printWithTime("one")
    }
    printWithTime("two")
    delay(2000L)*/

    val job = GlobalScope.launch {
        delay(1000L)
        printWithTime("world")
    }
    printWithTime("Hello, ")
    job.join()
}

fun example(): Unit {
    printWithTime("Start")

    GlobalScope.launch {
        delay(1000L)
        printWithTime("Hello")
    }

    /*thread {
        Thread.sleep(1000L)
        printWithTime("Hello")
    }*/

    Thread.sleep(2000)
    printWithTime("Stop")

    runBlocking {
        delay(1000)
        printWithTime("World")
    }

    Thread.sleep(2000)
    printWithTime("End")
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
    val deferredList = (1..1_000_000).map { n ->
        GlobalScope.async {
            workload(n)
        }
    }
    runBlocking {
        val sum = deferredList.sumBy {
            it.await()
        }
        println("Sum: $sum")
    }
}

suspend fun workload(n: Int): Int {
    delay(1000)
    return n
}

fun printWithTime(msg: String) {
//    val time = Date(System.currentTimeMillis()).toString()
//    println("$time: $msg")

    val dateFormat = DateFormat.getDateTimeInstance()
    // GMT-08:00
    dateFormat.timeZone = TimeZone.getTimeZone("GMT+08:00")
    println("${dateFormat.format(Date())}: $msg")
}