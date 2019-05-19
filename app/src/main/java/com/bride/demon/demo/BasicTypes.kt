package com.bride.demon.demo

import java.lang.StringBuilder
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * <p>Created by shixin on 2019-05-09.
 */
fun main() {
    val a: Int = 10000
    val boxedA: Int? = a
    val anotherBoxedA: Int? = a
    println(boxedA === anotherBoxedA)
    println(boxedA == anotherBoxedA)

    val aa: Int? = 1
    val ll: Long? = 1
    println(aa?.toLong() == ll)
    println(Float.NaN.compareTo(Float.POSITIVE_INFINITY))
    println(0.0.compareTo(-0.0))

    println(1 shl 2)
    println(1 and 3)
    println(1 xor 3)

    val price = "${'$'}9.9"
    println(price + " " + "\$9.9")

    println(text)

    printStrLength("")

    loop@ for (i in 1..10) {
        for (j in 1 until 10) {
            if (i == j){
                print("i = $i, j = $j; ")
                continue@loop
            }
        }
    }
    println()

    lock(ReentrantLock()) lit@{
        fool()
        return@lit
    }

    ff lit@ {
        fool()
        return@lit
    }

    // non-local returns
    sing lit@{
        println(it)
        return@lit
    }

    // character literal
    println("decimalDigitValue: ${decimalDigitValue('1')}")

    val root = TreeNode(1, null)
    val left = TreeNode(2, root)
    val right = TreeNode(3, root)
    println(left.findParentOfType(TreeNode::class.java))
    println(right.findParentOfType<TreeNode>())
    println(membersOf<StringBuilder>())
}

val i: Int = 0b01010101_10101010
val l: Long = 0xFF_A0_B1_C2L
val f: Float = 1.3F
val d: Double = 1.77e10
val b: Byte = 127
val s: Short = 32767
val ul: ULong = 1UL

fun decimalDigitValue(c: Char): Int {
    if (c !in '0'..'9') {
        throw IllegalArgumentException()
    }
    val result = c.toInt() - '0'.toInt()
    return result
}

val text = """
|for(c in str)
|   println(c)
""".trimMargin("|")

fun printStrLength(str: String?): Unit {
    str ?: return
    println("str = ${str.length}")
}

fun fool() {
    // in lambda expressions, return at labels
    // forEach is inline function, take lambda as parameter
    listOf(1, 3, 5, 7).forEach lit@{
        if (it == 5)
            return@lit
        print("it = $it, ")
    }

    // return from anonymous function
    arrayOf(2, 4, 6, 8).forEach(fun(value: Int) {
        if (value == 6)
            return
        print("value = $value, ")
    })

    run loop@{
        doubleArrayOf(1.1, 2.2, 3.3, 4.4).forEach {
            if (it == 3.3)
                return@loop
            print("it = $it, ")
        }
    }
    println("reachable")
}

inline fun lock(lock: Lock, noinline body: () -> Unit): Unit {
    lock.lock()
    try {
        body()
    } finally {
        lock.unlock()
    }
}

inline fun ff(crossinline body: () -> Unit) {
    val f = object: Runnable {
        override fun run() = body()
    }
}

inline fun sing(body: (String) -> Unit) {
    body("I like it.")
}

class TreeNode(var value: Int, var parent: TreeNode?) {
    override fun toString(): String {
        return "TreeNode[value = $value, parent = $parent]"
    }
}

fun <T> TreeNode.findParentOfType(clazz: Class<T>): T? {
    var p = parent
    while (p != null && !clazz.isInstance(p)) {
        p = p.parent
    }
    return p as T?
}

inline fun <reified T> TreeNode.findParentOfType(): T? {
    var p = parent
    while (p != null && p !is T) {
        p = p.parent
    }
    return p as T?
}

// For java, in runtime, instanceof operator is replaced by Class#isInstance(Object).
inline fun <reified T> membersOf() = T::class.members