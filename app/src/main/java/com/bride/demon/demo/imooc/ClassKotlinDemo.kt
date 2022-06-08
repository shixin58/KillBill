package com.bride.demon.demo.imooc

fun main() {
    val s = Simple(1)
    println(s.x)

    val xRef = SubSimple::x
    val simple = SubSimple(1)
    xRef.set(simple, 2)
    // 绑定receiver的属性引用
    val yRef = simple::y
    yRef.set(6L)
}

class Simple {
    var x: Int
    // secondary constructor
    constructor(x: Int) {
        this.x = x
    }
}

// primary constructor
class Simple1 constructor(x: Int) {
    var x: Int = x
}

open class Simple2(var x: Int, var y: Long): AbsSimple(), ISimple {
    override val prop: Int
        get() = 2

    var no: Int = 1// property
        get() = field// backing field
        set(value) {
            field = value
        }

    override fun life() {
        println("live a life")
    }

    override fun survive() {
        println("survival is important")
    }

    final override fun work() {
        super.work()
        println("from open to final")
    }
}

interface ISimple {
    val prop: Int
    fun life()
}

abstract class AbsSimple {
    abstract fun survive()
    open fun work() {

    }
}

class SubSimple(x: Int): Simple2(x, 3L) {

}