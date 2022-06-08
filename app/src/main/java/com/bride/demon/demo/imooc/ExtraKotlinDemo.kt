package com.bride.demon.demo.imooc

// 尽可能使用val，让程序含义更清晰；减少对外部成员的访问，方便函数式编程、单元测试；创建局部变量指向外部成员
fun main() {
    // 智能类型转换
    var name: String? = null// 局部变量
    name = "Max Shi"
    if (name != null) {
        println(name.length)
    }

    val animal: IAnimal = Animal()
    // 类型安全转换
    (animal as? Animal)?.eat()
    if (animal is Animal) {
        animal.eat()
    }

    // when表达式
    val c = when(val input = readLine()) {
        null -> 0
        else -> input.length
    }
    println("length = $c")

    // try-catch表达式
    val v = try {
        // 4.div(0)
        4/0
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
    println("4/0 = $v")

    // 运算符重载Operator Overloading
    // https://kotlinlang.org/docs/operator-overloading.html
}

interface IAnimal

class Animal: IAnimal {
    fun eat() {}
}