package com.bride.demon.demo

import kotlin.io.println
import kotlin.io.print
import kotlin.arrayOf

/**
 * <p>Created by shixin on 2019-05-07.
 */
fun main() {
    val a = 3
    val b = 5
    val max = if (a >= b) a else b
    println("max $max")

    when (max) {
        1, 3 -> println("max = 1 or 3")
        2 -> println("max = 2")
        !is Int -> println("max is Int")
        in 1..10 -> println("max is 1..10")
        else -> println("max = neither 1, 3 nor 2")
    }

    val day = false
    val night = when {
        day -> {
            println("day")
            false
        }
        else -> {
            println("night")
            true
        }
    }

    for (i in 8 downTo 0 step 2)
        print("i = $i, ")
    println()
    val arr = arrayOf("A", "B", "C", "D")
    for (j in arr.indices)
        print("arr[j] = ${arr[j]}, ")
    println()
    for ((index, value) in arr.withIndex())
        print("index = $index, value = $value; ")
    println()

    Customer("Max", 30)
}

// indirect inheritors
class OldTimes : NextDecade()