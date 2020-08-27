package com.bride.demon.demo

import android.os.Build
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
    println()

    val example = Example()
    println(example.prop)
    example.prop = "Max"
    println("example.p = ${example.prop}")
    poor { example }
    println()

    println(lazyValue)
    println(lazyValue)
    println()

    val user = UserNew()
    user.name = "Victor"
    user.name = "Jacob"

    val map = mapOf(
            "name" to "Victor",
            "age" to 30
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        println("sex is ${map.getOrDefault("sex", "lady")}")
    println("name = ${map["name"]}, age = ${map["age"]}")
    val person = Person(map)

    val mutableMap = mutableMapOf<String,Any>(
            "name" to "Max", "age" to 25
    )
    mutableMap["sex"] = "gentleman"
    val mutablePerson = MutablePerson(mutableMap)
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
    var value: String = "<no name>"

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        println("$thisRef, thank you for delegating ${property.name} to me!")
        return this.value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        this.value = value
        println("$value has been assigned to ${property.name} in $thisRef")
    }
}

class Example {
    var prop: String by Delegate()
}

// Delegated Properties
val lazyValue: String by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    println("initialization")
    "hello"
}

fun poor (compute: () -> Example) {
    val exam by lazy(compute)
    println("exam.p = ${exam.prop}")
}

class UserNew {
    /*var name: String by Delegates.observable("<no name>") {
        property, oldValue, newValue ->
        println("$oldValue -> $newValue")
    }*/
    var name: String by Delegates.vetoable("<no name>") {
        _, oldValue, newValue ->
        println("$oldValue -> $newValue")
        false
    }
}

class Person(val map: Map<String, Any?>) {
    val name: String by map
    val age: Int by map
}

class MutablePerson(val map: MutableMap<String, Any>) {
    val name: String by map
    val age: Int by map
}