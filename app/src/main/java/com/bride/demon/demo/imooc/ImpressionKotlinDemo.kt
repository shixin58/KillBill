package com.bride.demon.demo.imooc

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