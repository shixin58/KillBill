package com.bride.demon.demo.imooc

import java.io.File

fun main() {
    // 行参或返回值为函数类型的函数称为高阶函数
    println((0..5).map { "$it) " }[0])

    val fibonacciNext = fibonacci()
    repeat((0..10).count()) {
        println(fibonacciNext())
    }

    // 内联函数inline fun，编译时将实现替换到调用处，减少函数调用来优化性能
    val costNanos = cost {
        repeat(5) {
            println(it)
        }
    }
    println("cost $costNanos")

    (1..5).forEach {
        if (it==3) return@forEach// local return, 相当于continue
        if (it==6) return// non-local return，退出main
        println(it)
    }

    // 高阶函数use自动关闭资源
    // FileInputStream(File) -> InputStreamReader(InputStream) -> BufferedReader(Reader)
    File("app/build.gradle").inputStream().reader().buffered()
        .use {
            println(it.readLine())
        }

    // readText()扩展很便利
    File("build.gradle")
        .readText()
        .toCharArray()
        .filterNot(Char::isWhitespace)// List<Char>
        .groupBy { it }// Map<Char, List<Char>>
        .map { it.key to it.value.size }// List<Pair<Char, Int>>
        .let { println(it) }

    // DSL, Domain Specific Language领域特定语言
    // https://github.com/Kotlin/kotlinx.html
    // https://kotlinlang.org/docs/gradle.html
}

fun fibonacci(): () -> Long {
    var first = 0L
    var second = 1L
    return {
        val next = first + second
        val current = first
        first = second
        second = next
        current
    }
}

// crossinline禁止non-local return, noinline禁止内联
inline fun cost(crossinline block: () -> Unit): Long {
    val startNanos = System.nanoTime()
    block()
    return System.nanoTime() - startNanos
}

// 内联属性，没backing field的属性可内联getter/setter
var pocket: Long = 0L
var money: Long
    inline get() { return pocket }
    inline set(value) { pocket = value }