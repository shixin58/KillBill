package com.bride.demon.demo.imooc

// 主构造器至多1个，init块可以多个
// 推荐主构造+默认参数
// Gradle Source sets原理 https://docs.gradle.org/current/userguide/java_plugin.html
fun main() {
    val plant = Plant("Max")
}

/**
 * @param age 构造器参数，init块、属性初始化可见
 */
class Plant(var name: String, age: Int) {
    val age: Int// 不会默认初始化
    init {// 主构造器方法体
        this.age = age
    }

    constructor(age: Int): this("unknown", age)
}

// 构造同名的工厂函数
val plants = HashMap<String, Plant>()
fun Plant(name: String): Plant {
    return plants[name]
        ?:Plant(name, 1).also { plants[name] = it }
}