package com.bride.demon.demo

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * <p>Created by shixin on 2019-05-16.
 */
fun main() {
    // 接口代理
    val b = BaseImpl(23)
    val derived = BigDerived(b)
    derived.printMessage()
    derived.printMessageLine()
    derived.print()
    println("${derived.message}\n")

    // 属性代理
    val example = Example()
    // getValue()
    example.prop
    // setValue()
    example.prop = "Max"
    poor { example }

    // by lazy
    println()
    println(lazyValue)
    println(lazyValue)

    // vetoable
    println()
    val user = UserNew()
    user.name = "Victor"
    user.name = "Jacob"

    // 属性代理
    println()
    val map = mapOf(
            "name" to "Victor",
            "age" to 30
    )
    val person = Person(map)
    println("Person[name = ${person.name}, age = ${person.age}]")
    val mutableMap = mutableMapOf<String,Any>(
            "name" to "Max", "age" to 25
    )
    mutableMap["sex"] = "gentleman"
    val mutablePerson = MutablePerson(mutableMap)
    println("MutablePerson[name = ${mutablePerson.name}, age = ${mutablePerson.age}, sex = ${mutablePerson.sex}]")

    // observable属性代理
    println()
    val stateManager = StateManager()
    stateManager.state = 3

    // 通过File或SharedPreferences实现属性代理
    // T.javaClass等价于Object.getClass
    // Class#getResourceAsStream(String)用Path得到InputStream，Properties#load(InputStream)保存属性至Properties
    // Class#getResource(String)用Path得到URL
    // Properties#getProperty(key, defaultValue), Properties#setProperty(key, value)
    // File(URL#toURI()).outputStream()，Properties#store(OutputStream, comments)
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
        println("BaseImpl#print() $message")
    }

    override fun printMessage() {
        println("BaseImpl#printMessage() $message")
    }

    override fun printMessageLine() {
        println("BaseImpl#printMessageLine() $message")
    }
}

// derive衍生/获得/提取
class BigDerived(val b: IBase) : IBase by b {
    override val message: String
        get() = "Message of Derived"
    override fun printMessage() {
        println("BigDerived#printMessage() $message")
    }
}

class Delegate {
    var value: String = "<no name>"

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        println("${thisRef?.javaClass?.simpleName}, thank you for delegating ${property.name} to me!")
        return this.value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        this.value = value
        println("$value has been assigned to ${property.name} in ${thisRef?.javaClass?.simpleName}")
    }
}

class Example {
    var prop: String by Delegate()
}

// Delegated Properties
val lazyValue: String by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
    println("initialization of lazyValue")
    "hello"
}

fun poor (compute: () -> Example) {
    val exam by lazy(compute)
    exam.prop
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

class Person(map: Map<String, Any>) {
    val name: String by map
    val age: Int by map
}

class MutablePerson(map: MutableMap<String, Any>) {
    val name: String by map
    val age: Int by map
    val sex: String by map
}

class StateManager {
    var state: Int by Delegates.observable(0) {
        property, oldValue, newValue ->
        println("state changed from $oldValue to $newValue")
    }
}