package com.bride.demon.demo.imooc

import kotlin.reflect.KFunction3

fun main(vararg args: String) {
    // 传递函数引用
    exe(Dog::bite)
    exe2(Dog::bite)
    exe3(Dog::eat)

    multiParams("法拉利", "奥迪", "宾利")

    // 默认参数和具名参数
    defaultParam(x = 1, y = "joy")

    // 借助Pair和Triple实现伪多返回值
    val (x, y) = multiReturnVal()
    println("multiReturnVal: x=$x, y=$y")

//    "2 * 3".split(" ")
    println(args.joinToString(" "))

    // region +折叠
    val map = mapOf(
        "+" to ::plus,
        "-" to ::minus,
        "*" to ::times,
        "/" to ::div)
    println(map["*"]!!.invoke(3, 4))
    // endregion

    println("nb".times(10))
}

fun exe(f: (Dog, String) -> Unit) {
    f.invoke(Dog(), "哈士奇")
}

fun exe2(f: Dog.(String) -> Unit) {
    f(Dog(), "藏獒")
}

fun exe3(f: KFunction3<Dog, String, String, Boolean>) {
    f(Dog(), "泰迪", "Noodle")
}

class Dog {
    fun bite(name: String): Unit {
        println(name)
    }

    fun eat(name: String, food: String): Boolean {
        println("$name eat $food")
        return true
    }
}

// 变长参数
fun multiParams(vararg params: String) {
    println(params.contentToString())
}

fun defaultParam(x: Int, y: String, z: Long = 0L) {
    println("x=$x, y=$y, z=$z")
}

fun multiReturnVal(): Pair<Int, Int> {
    return Pair(1, 2)
}

fun plus(arg0: Int, arg1: Int): Int {
    return arg0 + arg1
}

fun minus(arg0: Int, arg1: Int): Int {
    return arg0 - arg1
}

fun times(arg0: Int, arg1: Int): Int {
    return arg0 * arg1
}

fun div(arg0: Int, arg1: Int): Int {
    return arg0 / arg1
}

fun String.times(cnt: Int): String {
    return (1..cnt).joinToString(separator = "", transform = {
        this
    })
}