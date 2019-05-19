package com.bride.demon.demo

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * <p>Created by shixin on 2019-05-16.
 */
fun main() {
    val b = BaseImpl(23)
    val derived = BigDerived(b)
    derived.printMessage()
    derived.printMessageLine()
    derived.print()
    println(derived.message)

    val example = Example()
    println(example.p)
    example.p = "Max"

    println(lazyValue)
    println(lazyValue)

    val user = UserNew()
    user.name = "Victor"
    user.name = "Jacob"
}

interface IBase {
    val message: String
    fun print()
    fun printMessage()
    fun printMessageLine()
}

class BaseImpl(val i: Int) : IBase {
    override val message: String
        get() = "BaseImpl: $i"

    override fun print() {
        println(message)
    }

    override fun printMessage() {
        println(i)
    }

    override fun printMessageLine() {
        println(i)
    }
}

class BigDerived(val b: IBase) : IBase by b {
    override val message: String
        get() = "Message of Derived"
    override fun printMessage() {
        println("up to date")
    }
}

class Delegate {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating ${property.name} to me!"
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to ${property.name} in $thisRef")
    }
}

class Example {
    var p: String by Delegate()
}

// Delegated Properties
val lazyValue: String by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    println("initialization")
    "hello"
}

class UserNew {
    /*var name: String by Delegates.observable("<no name>") {
        property, oldValue, newValue ->
        println("$oldValue -> $newValue")
    }*/
    var name: String by Delegates.vetoable("<no name>") {
        property, oldValue, newValue ->
        println("$oldValue -> $newValue")
        false
    }
}