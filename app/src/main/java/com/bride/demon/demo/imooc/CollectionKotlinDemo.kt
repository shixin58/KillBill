package com.bride.demon.demo.imooc

fun main() {
    // 不可变List
    val list = listOf(1, 3, 5)
    val mutableList = mutableListOf(2, 4, 6)
    mutableList += 3
    mutableList -= 6

    // infix fun中缀表达式, A.to(B)等价于A to B
    val map = mapOf<String, Any>("string" to "banana", "number" to 1)

    val pair = "a" to "A"
    // 解构表达式
    val (x, y) = pair

    val triple = Triple("X", "Y", "Z")
    val (a, b, c) = triple

    // typealias
    val set = HashSet<Char>()
}