package com.bride.demon.demo

import android.content.Context
import android.util.AttributeSet
import android.view.View

import kotlin.also

/**
 * <p>Created by shixin on 2019-05-07.
 */
class Customer public constructor(val name: String) {

    val firstProperty = "first property name = $name".also(::println)

    init {
        println("primary initializer")
    }

    val secondProperty = "second property name.length() = ${this.name.length}".also(System.err::println)

    init {
        println("second initializer")
    }

    public constructor(name: String, age: Int) : this(name) {
        println("secondary constructor")
    }
}

class DontCreateMe internal constructor(val id: Int = 0) : Any() {
    init {
        println("primary initializer")
    }
}

class MyView : View {

    constructor(ctx : Context) : super(ctx)

    constructor(ctx : Context, attrs : AttributeSet) : super(ctx, attrs)
}

fun main() {
    Customer("Victor", 30)
    DontCreateMe()
    Derived().f()

    val max = User(name = "Max", age = 20)
    val oldMax = max.copy(age = 30)
    // destructuring, Pair/Triple
    val (name, age) = max
    println("name = $name, age = $age")
}

open class Base() {

    open val a: Int get() {
        return 1
    }

    lateinit var z: String

    open val v: String = "boy".also { println("Initializing v in Base") }

    open fun f(): Unit {
        System.out.println("Base#f()")
        if (Base::z.isLateinit) {
            z = "大器晚成"
            println("z = $z")
        }
    }
}

class Derived : Base(), Foo {
    override val count: Int
        get() = super.a + 1

    final override val a: Int = 1.also { println("Initializing a in Derived") }

    // backing fields
    override var v: String = "Good"
        set(value) {
            if (value != null) field = value
        }

    var k: Int? = 3
        private set

    final override fun f() {
        super<Base>.f()
        super<Foo>.f()
        System.out.println("Derived#f()")
    }

    abstract inner class Sub {

        fun s() {
            super<Base>@Derived.f()
            println("Base#a = ${super@Derived.a}")
        }

        abstract fun h()
    }
}

internal interface Foo : Edible, Capable {

    val count: Int// abstract

    val name: String
        get() = "$count"

    fun f() {
        println("Foo#f()")
    }
}

internal interface Edible {

}

internal interface Capable {

}

// Top-level, Compile-Time Constants
const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deprecated"
@Deprecated(SUBSYSTEM_DEPRECATED) fun foo() {

}

data class User(val name: String = "Victor", val age: Int = 30)