package com.bride.demon.demo.imooc

fun main() {
    println(maxOf("a", "b"))

    // Nothing是Double的子类，那么List<Nothing>是List<Double>的子类
    val list = List.Cons<Double>(1.0, List.Nil)

    // WhiteFlower是Flower的子类，则Product<WhiteFlower>是Product<Flower>的子类
    val product: Product<Flower> = WhiteFlowerProduct()

    // Comparable是逆变的例子，Function是协变+逆变的例子
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
    // 消费者，逆变点
    fun put(t: T) {}
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