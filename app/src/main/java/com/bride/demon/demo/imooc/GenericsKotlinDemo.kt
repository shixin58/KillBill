package com.bride.demon.demo.imooc

import java.lang.reflect.ParameterizedType
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap
import kotlin.collections.HashMap
import kotlin.reflect.KProperty
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

    // 模拟Scala Self Type
    ConfirmNotificationBuilder()
        .title("Hello")
        .content("World")
        .onConfirm { println("onConfirm") }
        .onCancel { println("onCancel") }
        .build().onConfirm()

    // 基于泛型实现Model实例注入
    initModels()
    val mainViewModel = MainViewModel()
    mainViewModel.databaseModel.query("select * from mysql.user").let(::println)
    mainViewModel.networkModel.get("https://www.imooc.com").let(::println)
    mainViewModel.spModel.hello()
    mainViewModel.spModel2.hello()
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

typealias OnConfirm = () -> Unit
typealias OnCancel = () -> Unit

private val EmptyFunction = {}

open class Notification(val title: String, val content: String)

class ConfirmNotification(
    title: String,
    content: String,
    val onConfirm: OnConfirm,
    val onCancel: OnCancel
): Notification(title, content)

interface SelfType<Self> {
    val self: Self
        get() = this as Self
}

open class NotificationBuilder<Self: NotificationBuilder<Self>>: SelfType<Self> {
    protected var title: String = ""
    protected var content: String = ""

    fun title(title: String): Self {
        this.title = title
        return self
    }

    fun content(content: String): Self {
        this.content = content
        return self
    }

    open fun build(): Notification {
        return Notification(title, content)
    }
}

class ConfirmNotificationBuilder: NotificationBuilder<ConfirmNotificationBuilder>() {
    private var onConfirm: OnConfirm = EmptyFunction
    private var onCancel: OnCancel = EmptyFunction

    fun onConfirm(onConfirm: OnConfirm): ConfirmNotificationBuilder {
        this.onConfirm = onConfirm
        return this
    }

    fun onCancel(onCancel: OnCancel): ConfirmNotificationBuilder {
        this.onCancel = onCancel
        return this
    }

    override fun build(): ConfirmNotification {
        return ConfirmNotification(title, content, onConfirm, onCancel)
    }
}

abstract class AbsModel(name: String? = null) {
    val name: String = name?.takeIf { it.isNotBlank() }?:this.javaClass.simpleName

    init {
        Models.run { this@AbsModel.register(this@AbsModel.name) }
    }
}

class DatabaseModel: AbsModel() {
    fun query(sql: String): Int = 0
}

class NetworkModel: AbsModel() {
    fun get(url: String): String = """{"code": 0}"""
}

class SpModel(name: String? = null): AbsModel(name) {
    fun hello() {
        println("HelloWorld, ${this.name}")
    }
}

object Models {
    private val modelMap = ConcurrentHashMap<String,AbsModel>()

    fun AbsModel.register(name: String = this.javaClass.simpleName) {
        modelMap[name] = this
    }

    fun <T: AbsModel> String.get(): T {
        return modelMap[this] as T
    }
}

fun initModels() {
    DatabaseModel()
    NetworkModel()
    SpModel()
    SpModel("SpModel2")
}

// 定义属性代理
object ModelDelegate {
    operator fun <T: AbsModel> getValue(thisRef: Any, property: KProperty<*>): T {
        return Models.run {
            property.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                .get()
        }
    }
}

class MainViewModel {
    val databaseModel: DatabaseModel by ModelDelegate
    val networkModel: NetworkModel by ModelDelegate
    val spModel: SpModel by ModelDelegate
    val spModel2: SpModel by ModelDelegate
}