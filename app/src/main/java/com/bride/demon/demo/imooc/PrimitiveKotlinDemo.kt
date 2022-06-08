package com.bride.demon.demo.imooc

fun main() {
    val uByte: UShort = 1u
    // 顶级函数
    println("A")
    val msg = String("KFC".toCharArray())

    val a1 = DoubleArray(2){ (it+1).toDouble() }
    println("${a1.contentToString()}, ${a1.size}")
    for (v in a1) {
        print("$v, ")
    }
    println(a1.size)

    for (i in a1.indices) {
        print("${a1[i]}, ")
    }
    println()

    a1.forEach {
        print("$it, ")
    }
    println()

    if (a1.any { it == 1.0 }) {
        println("contains")
    }

    val a2 = intArrayOf(1, 2, 3)
    if (4 !in a2) {
        println("not contains")
    }

    // 创建离散闭区间IntRange
    val intRange = 1..10

    // 左闭右开区间CharRange
    val charRangeExclusive = 'a' until 'z'

    // 创建闭区间IntProgression，步长2
    val intRangeWithStep = 1..10 step 2

    // 倒序区间LongProgression
    val longRange = 100L downTo 1L

    // 数学上的区间ClosedFloatingPointRange
    val doubleRange = 1.0 .. 3.0

    val uIntRange = 1U .. 5U
    val uLongRange = 1UL ..100UL

    // sout
    println(intRange.joinToString(separator = ","))
    println(doubleRange.toString())
}