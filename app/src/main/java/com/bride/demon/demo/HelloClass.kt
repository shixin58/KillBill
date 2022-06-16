package com.bride.demon.demo

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * <p>Created by shixin on 2019-05-07.
 */
fun main() {
    Customer("Victor", 30)
    DontCreateMe()
    Derived().f()

    val max = User(name = "Max", age = 20)
    val oldMax = max.copy(age = 30)
    // destructuring, Pair/Triple
    val (name, age) = max
    println("name = $name, age = $age")

    // 模拟内部类
    val i = Outer.Nested().get()
    val k = ShorterNested().get()
    val j = Outer().Inner().retrieve()
    println("i = $i, j = $j, k = $k")

    // 饿汉式单例 object，类名跟对象名相同
    DataProviderManager.provideData()
    // 工厂方法
    val factory = MyClass.Companion
    factory.create()

    // 模拟函数类型
    val f: (Int) -> Boolean = { it > 0 }
    println(foo(f))
    val p: Predicate<Int> = { it > 0 }
    println(listOf(1, -1).filter(p))

    // 数据类data class，自动添加copy/equals/hashCode/toString方法，通过component来解构
    // 不可被继承(AllOpen插件)，不可无参构造(NoArg插件)

    // 枚举enum class
    println("${Weekday.Monday.name}, ${Weekday.Monday.ordinal}, ${Weekday.Monday.abbr}")
    Weekday.Monday.run()

    // 密封类sealed class
    // 特殊的抽象类，直接子类定义在相同文件中，主、副构造器protected/private
    println(f(Past("Poor")))
    println(f(NotAString))

    // 内联类inline class
    // 对另一个类型的包装，类似Java装箱类型。编译优化，自动拆箱、尽可能使用被包装类型，如UInt
    // 属性不允许backing field，不能继承父类、可实现接口，有且仅有1个只读属性
    // 使用场景，内联类模拟枚举来优化内存。
    val boxInt = BoxInt(5)
    println(boxInt + BoxInt(3))

    // 函数式编程Functional Programming。λ演算Lambda Calculus，接受函数作为参数和返回值
    val (x, y, z) = Present("Strong")
    println("解构：$x, $y, $z")
}

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

open class Base {
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
    override fun struggle() {
    }

    override fun eat() {
    }

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
    fun eat()
}

internal interface Capable {
    fun struggle()
}

// 实现单父类、多个接口的匿名内部类
// TypeScript(JS平台)支持交叉类型、联合类型
private fun func() = object : Edible, Capable {
    override fun eat() {
    }

    override fun struggle() {
    }
}

// Top-level, Compile-Time Constants
const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deprecated"
@Deprecated(SUBSYSTEM_DEPRECATED) fun foo() {

}

data class User(val name: String = "Victor", val age: Int = 30)

sealed class Times(val idx: Int){
    var desc: String = "invalid"
    constructor(idx: Int, desc: String): this(idx) {
        this.desc = desc
    }
}
data class Past(val state: String) : Times(0)
class Present(val state: String) : Times(1)
data class Future(val state: String) : Times(2)
object NotAString : Times(3, "Not a String")
open class NextDecade : Times(4, "Next Decade")

fun f(v: Times): String = when(v) {
    is Past -> v.state
    is Present -> v.state
    is Future -> v.state
    NotAString -> v.desc
    is NextDecade -> v.desc
}

operator fun Present.component1(): Int {
    return idx
}

operator fun Present.component2(): String {
    return desc
}

operator fun Present.component3(): String {
    return state
}

// anonymous inner class, lambda expressions
val listener = View.OnClickListener {
    when(it.id) {
    }
}

val onLongClickListener = object : View.OnLongClickListener {
    override fun onLongClick(v: View): Boolean {
        return false
    }
}

class Outer {
    class Nested {
        fun get() = 1
    }

    inner class Inner {
        fun retrieve(): Int {
            return 2
        }
    }

    object InnerSingleton {
        fun work() {
        }
    }
}
typealias ShorterNested = Outer.Nested

abstract class DataProvider {
    abstract fun provideData()
}

object DataProviderManager : DataProvider() {
    // 去掉getter/setter
    @JvmField var x: Int = 3
    // Kotlin模拟Java的static成员
    @JvmStatic fun y() {}

    override fun provideData() {
        println("DataProviderManager#provideData()")
    }
}

interface Factory<T> {
    fun create(): T
}

class MyClass {
    // 伴生对象，可继承类或实现接口
    companion object : Factory<MyClass> {
        override fun create(): MyClass {
            return MyClass()
        }
    }
}

// function type
typealias Predicate<T> = (T) -> Boolean

fun foo(p: Predicate<Int>) = p(24)

enum class Weekday(val abbr: String): Runnable {
    Monday("Mon") {
        override fun run() {
            super.run()
            println("Just Monday")
        }
    },
    Tuesday("Tue"), Wednesday("Wed"), Thursday("Thu"), Friday("Fri");

    override fun run() {
        println("For every weekday")
    }
}

@JvmInline
value class BoxInt(val value: Int) {
    val name: String
        get() = "BoxInt[$value]"

    operator fun plus(other: BoxInt): BoxInt {
        return BoxInt(value + other.value)
    }
}