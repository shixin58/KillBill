@file:JvmName("KotlinAnnotations")
@file:JvmMultifileClass
package com.bride.demon.demo.imooc

import java.io.IOException

@Synchronized
fun synchronizedFunction() {

}

val lock = Any()
fun synchronizedBlock() {
    // 高阶函数，内部使用monitorEnter()和monitorExit()
    synchronized(lock) {

    }
}

@Throws(IOException::class)
fun throwExceptions() {

}

@Volatile
var volatileProperty: Int = 0