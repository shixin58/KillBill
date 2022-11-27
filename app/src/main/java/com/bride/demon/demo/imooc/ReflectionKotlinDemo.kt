package com.bride.demon.demo.imooc

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

fun main() {
    val group = Group(1, "China", "Asia")
    val man = Man(1, "Max", group)
    val copiedMan = man.copy()// shallow copy
    println(man === copiedMan)
    println(man.group === copiedMan.group)// true

    val deepCopiedMan = man.deepCopy()
    println(man.group === deepCopiedMan.group)
    println(deepCopiedMan.toString())

    val userDetail = UserDetail(1, "Max", "Gentleman", 33)
    val userMap = mapOf(
        "id" to 1,
        "name" to "Max",
        "gender" to "Gentleman",
        "age" to 33
    )
    println(userDetail.mapAs<UserDetail,UserBrief>())
    println(userMap.mapAs<UserBrief>())

    val activity = Activity()
    activity.onCreate()
    activity.onDestroy()
}

data class Man(val id: Int, val name: String, val group: Group)
data class Group(val id: Int, val name: String, val location: String)

fun <T : Any> T.deepCopy(): T {
    if (!this::class.isData) {
        return this
    }
    return this::class.primaryConstructor!!.let { primaryConstructor ->
        primaryConstructor.parameters.associate { parameter ->
            val value = (this::class as KClass<T>).memberProperties.first { it.name == parameter.name }
                .get(this)
            if ((parameter.type.classifier as? KClass<*>)?.isData == true) {
                parameter to value?.deepCopy()
            } else {
                parameter to value
            }
        }
            .let(primaryConstructor::callBy)// KCallable#callBy(), KFunction是KCallable子类
    }
}

/**
 * VO(业务展示Model)
 */
data class UserBrief(val id: Int, val name: String)

/**
 * DTO(网络请求Model)
 */
data class UserDetail(val id: Int, val name: String, val gender: String, val age: Int)

/**
 * Model映射：网络请求Model到业务展示Model。内联泛型特化(inline+reified)来拿到KClass。
 */
inline fun <reified From : Any,reified To : Any> From.mapAs(): To {
    return From::class.memberProperties.associate { it.name to it.get(this) }.mapAs()
}

inline fun <reified To : Any> Map<String,Any?>.mapAs(): To {
    return To::class.primaryConstructor!!.let {
        it.parameters.associateWith { parameter ->
            (this[parameter.name]?: if(parameter.type.isMarkedNullable) null
            else throw IllegalArgumentException("${parameter.name} is required but missing."))
        }.let(it::callBy)
    }
}

fun <T : Any> releasableNotNull() = ReleasableNotNull<T>()

class ReleasableNotNull<T : Any> : ReadWriteProperty<Any,T>{
    private var value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value?: throw IllegalStateException("Already released or not initialized")
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this.value = value;
    }

    fun isInitialized() = value != null

    fun release() {
        value = null
    }
}

inline val KProperty0<*>.isInitialized: Boolean
    get() {
        isAccessible = true
        return (this.getDelegate() as? ReleasableNotNull<*>)?.isInitialized()
            ?: throw IllegalAccessException("Delegate is not an instance of ReleasableNotNull or is null.")
    }

fun KProperty0<*>.release() {
    isAccessible = true
    (this.getDelegate() as? ReleasableNotNull<*>)?.release()
        ?: throw IllegalAccessException("Delegate is not an instance of ReleasableNotNull or is null.")
}

class Bitmap(val width: Int, val height: Int)

class Activity {
    private var bitmap by releasableNotNull<Bitmap>()

    fun onCreate() {
        println(this::bitmap.isInitialized)
        bitmap = Bitmap(1080, 1920)
        println(::bitmap.isInitialized)
    }

    fun onDestroy() {
        println(::bitmap.isInitialized)
        ::bitmap.release()
        println(::bitmap.isInitialized)
    }
}