package com.bride.demon.demo.imooc

import java.lang.reflect.ParameterizedType
import kotlin.reflect.full.declaredFunctions

fun main() {
    println(maxOf("a", "b"))

    // Nothing是Double的子类，那么List<Nothing>是List<Double>的子类
    val list = List.Cons<Double>(1.0, List.Nil)

    // WhiteFlower是Flower的子类，则Product<WhiteFlower>是Product<Flower>的子类
    val product: Product<Flower> = WhiteFlowerProduct()

    // Comparable是逆变的例子，Function是协变+逆变的例子
    val numbers: Map<String,Number> = mapOf("a" to 0)
    // 声明为协变的类出现逆变点
    val num = numbers.getOrDefault("b", 0.1)
    println(num)

    // 星投影Star Projection
    val hashMap: HashMap<*,*> = HashMap<String, Int>()

    // 打印泛型参数类型
    IWaste::class.declaredFunctions.first { it.name == "getWastes" }
        .returnType.arguments
        .forEach { println(it) }
    IWaste::class.java.getDeclaredMethod("getWastes")
        .genericReturnType.safeAs<ParameterizedType>()?.actualTypeArguments
        ?.forEach { println(it) }

    // 通过signature拿到泛型实参，混淆时记得keep signature。
    val subType = SubType()
    subType.typeParameter.let(::println)
    subType.typeParameterJava.let(::println)
}

// 泛型约束
fun <T:Comparable<T>> maxOf(a: T, b: T): T {
    return if (a > b) a else b
}

// 用where定义多个约束
fun <T> callMax(a: T, b: T): Unit
    where T: Comparable<T>, T: () -> Unit {
        return if (a>b) a() else b()
}

// 协变(Covariance) - out，逆变(Contravariance) - in
// Java对应协变<? extends Object>(子类通配符)，对应逆变<? super T>(超类通配符)
sealed class List<out T> {
    // Nothing是所有类型的子类
    object Nil: List<Nothing>()
    data class Cons<E>(val head: E, val tail: List<E>): List<E>()
}

open class Flower
class WhiteFlower: Flower()

interface Product<out T> {
    // 协变只能读取不能写入
    // 生产者，协变点
    fun produce(): T
//    fun add(t: T)
}

class WhiteFlowerProduct: Product<WhiteFlower> {
    override fun produce(): WhiteFlower {
        return WhiteFlower()
    }
}

open class Waste
class DryWaste: Waste()

// 声明逆变泛型形参T
class Dustbin<in T: Waste> {
    // 声明为逆变的类出现协变点
    private val list = mutableListOf<@UnsafeVariance T>()

    // 消费者，逆变点
    fun put(t: T) {
        list += t
        // contains协变出现在逆变的位置，保证只读不写就行
    }
}

fun contravariant() {
    val dustbin: Dustbin<Waste> = Dustbin<Waste>()
    val dryWasteDustbin: Dustbin<DryWaste> = dustbin

    val waste = Waste()
    val dryWaste = DryWaste()

    dustbin.put(waste)
    dustbin.put(dryWaste)

    dryWasteDustbin.put(dryWaste)
}

interface IWaste {
    fun getWastes(): Map<Int,Waste>
}

fun <T> Any.safeAs(): T? {
    return this as? T
}

abstract class SuperType<T> {
    // 属性代理
    val typeParameter by lazy {
        this::class.supertypes.first().arguments.last().type!!
    }

    val typeParameterJava by lazy {
        this.javaClass.genericSuperclass!!.safeAs<ParameterizedType>()!!
            .actualTypeArguments.last()!!
    }
}

class SubType: SuperType<String>()