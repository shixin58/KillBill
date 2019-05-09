package com.bride.demon.demo

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * <p>Created by shixin on 2019-05-09.
 */
fun main() {
    val a: Int = 10000
    val boxedA: Int? = a
    val anotherBoxedA: Int? = a
    println(boxedA === anotherBoxedA)
    println(boxedA == anotherBoxedA)

    val aa: Int? = 1
    val ll: Long? = 1
    println(aa?.toLong() == ll)
    println(Float.NaN.compareTo(Float.POSITIVE_INFINITY))
    println(0.0.compareTo(-0.0))

    println(1 shl 2)
    println(1 and 3)
    println(1 xor 3)

    val price = "${'$'}9.9"
    println(price + " " + "\$9.9")

    println(text)

    printStrLength("")

    loop@ for (i in 1..10) {
        for (j in 1 until 10) {
            if (i == j){
                print("i = $i, j = $j; ")
                continue@loop
            }
        }
    }
    println()

    lock(ReentrantLock()) {
        fool()
    }

    ff {
        fool()
    }

    // character literal
    println("decimalDigitValue: ${decimalDigitValue('1')}")
}

val i: Int = 0b01010101_10101010
val l: Long = 0xFF_A0_B1_C2L
val f: Float = 1.3F
val d: Double = 1.77e10
val b: Byte = 127
val s: Short = 32767
val ul: ULong = 1UL

fun decimalDigitValue(c: Char): Int {
    if (c !in '0'..'9') {
        throw IllegalArgumentException()
    }
    val result = c.toInt() - '0'.toInt()
    return result
}

val text = """
|for(c in str)
|   println(c)
""".trimMargin("|")

fun printStrLength(str: String?): Unit {
    str ?: return
    println("str = ${str.length}")
}

fun fool() {
    listOf(1, 3, 5, 7).forEach lit@{
        if (it == 5)
            return@lit
        print("it = $it, ")
    }
    arrayOf(2, 4, 6, 8).forEach(fun(value: Int) {
        if (value == 6)
            return
        print("value = $value, ")
    })
    run loop@{
        doubleArrayOf(1.1, 2.2, 3.3, 4.4).forEach {
            if (it == 3.3)
                return@loop
            print("it = $it, ")
        }
    }
    println("reachable")
}

inline fun lock(lock: Lock, noinline body: () -> Unit): Unit {
    lock.lock()
    try {
        body()
    } finally {
        lock.unlock()
    }
}

inline fun ff(crossinline body: () -> Unit) {
    val f = object: Runnable {
        override fun run() = body()
    }
}