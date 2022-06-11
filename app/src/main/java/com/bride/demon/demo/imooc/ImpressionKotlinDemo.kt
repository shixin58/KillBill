package com.bride.demon.demo.imooc

import java.util.concurrent.Executors

fun main() {
    // 局部变量指向函数类型
    val func = fun() {
        println("匿名函数调用")
    }
    func()

    val func1 = { _: Int ->
        println("Lambda表达式")
    }
    func1.invoke(1)

    val func2: (Int) -> Unit = {
        println("单参默认it")
    }
    func2(2)

    // Java8的Lambda表达式指的是单方法接口语法糖，SAM(Single Abstract Method)
    val executor = Executors.newCachedThreadPool()
    // Kotlin的Lambda有类型()->Unit，本质是函数。Java8的Lambda没自己的类型，本质是SAM。
    executor.execute { println("SAM转换(Lambda -> 匿名内部类)") }
    // 编译后相当于
    executor.execute(object : Runnable {
        override fun run() {
            { println("SAM转换(Lambda -> 匿名内部类)") }.invoke()
        }
    })
    // 匿名内部类简写为
    val task = Runnable {
        println("SAM转换(Lambda -> 匿名内部类)")
    }

    val persons = HashSet<Person>()
    repeat((0..5).count()) { persons += Person("Max", it) }
    println(persons.size)

    println("Hello, Brave Heart".windowed(2, 1) {
        it == "He"
    }.count { it })
}

class Person(val name: String, val age: Int) {
    override fun equals(other: Any?): Boolean {
        val other = (other as? Person)?:return false
        return this.name==other.name && this.age==other.age
    }

    override fun hashCode(): Int {
        return 1 + 3*name.hashCode() + 5*age
    }
}