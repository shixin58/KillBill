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

    // 集合变换和序列filter/map/flatMap/zip
    val integers = listOf(0, 1, 2, 3, 4, 5, 6)
    // 懒序列，需要的时候才执行，优化性能
    integers.asSequence()
        .filter {
            println("filter $it")
            it % 3 == 0
        }.map {
            println("map $it")
            it * 2 + 1
        }.forEach { println("forEach $it") }// 水龙头/阀门，Terminal Operator

    // Int -> Iterable/Sequence
    integers.asSequence()
        .flatMap {
            (0..it).asSequence()
    }.joinToString()
        .let(::println)

    integers.zip('a'..'d') {
        left, right -> "$left$right"
    }.joinToString().let(::println)

    // 集合聚合操作sum/reduce/fold, Terminal Operator
    // accumulator蓄电池/累加器
    println(integers.fold(StringBuilder()) {
            acc, element -> acc.append(element)
    })

    println(integers.sum())

    println(integers.reduce { acc, i ->
        acc + i
    })

    val intMap = hashMapOf('a' to 1, 'b' to 2, 'c' to 3)
    println(intMap.map { "${it.key}='${it.value}'" }
        .joinToString(separator = " "))
}