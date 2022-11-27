@file:JvmName("KotlinAnnotations")
@file:JvmMultifileClass
package com.bride.demon.demo.imooc

import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

fun hello() {

}

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class FieldName(val name: String)

// @Retention默认RUNTIME
@Target(AnnotationTarget.CLASS)
annotation class MappingStrategy(val kClass: KClass<out NameStrategy>)

interface NameStrategy {
    fun mapTo(name: String): String
}

// 类似布局文件名下划线(Underscore)的形式被自动转换为驼峰(Camel)形式
object UnderscoreToCamel: NameStrategy {
    override fun mapTo(name: String): String {
        return name.split("_")
            .mapIndexed { index, s ->
                when(index) {
                    0 -> s
                    else -> s.replaceFirstChar { firstChar -> firstChar.uppercaseChar() }
                }
            }
            .fold(StringBuilder()) { acc, s -> acc.append(s) }
            .toString()
        /*return name.toCharArray().fold(StringBuilder()) {
            acc, c ->
            when(acc.lastOrNull()) {
                '_' -> acc[acc.lastIndex] = c.uppercaseChar()
                else -> acc.append(c)
            }
            acc
        }.toString()*/
    }
}

object CamelToUnderscore: NameStrategy {
    // htmlUrl -> html_url
    override fun mapTo(name: String): String {
        return name.toCharArray().fold(StringBuilder()) {
            acc, c ->
            if (c.isUpperCase()) {
                acc.append('_').append(c.lowercaseChar())
            } else {
                acc.append(c)
            }
        }.toString()
    }
}

@MappingStrategy(kClass = CamelToUnderscore::class)
data class UserVO(
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String
)

data class UserDTO(
    var id: Int,
    var login: String,
    var avatar_url: String,
    var url: String,
    var html_url: String
)

inline fun <reified From : Any,reified To : Any> From.convertAs(): To {
    return From::class.memberProperties.associate { it.name to it.get(this) }.convertAs()
}

inline fun <reified To : Any> Map<String,Any?>.convertAs(): To {
    return To::class.primaryConstructor!!.let {
        it.parameters.associateWith { parameter ->
            (this[parameter.name]
                ?: parameter.annotations.filterIsInstance<FieldName>().firstOrNull()?.name?.let(this::get)
                ?: To::class.findAnnotation<MappingStrategy>()?.kClass?.objectInstance?.mapTo(parameter.name!!)?.let(this::get)
                ?: if(parameter.type.isMarkedNullable) null
                else throw IllegalArgumentException("${parameter.name} is required but missing."))
        }.let(it::callBy)
    }
}

fun main() {
    val userDTO = UserDTO(
        1,
        "Max",
        "https://api.github.com/users/bennyhuo",
        "https://api.github.com/users/bennyhuo",
        "https://github.com/bennyhuo")

    val userMap = mapOf(
        "id" to 1,
        "login" to "Max",
        "avatar_url" to "https://api.github.com/users/bennyhuo",
        "url" to "https://api.github.com/users/bennyhuo",
        "html_url" to "https://github.com/bennyhuo"
    )

    val userVO: UserVO = userDTO.convertAs()
    println(userVO)

    val userVOFromMap: UserVO = userMap.convertAs()
    println(userVOFromMap)
}