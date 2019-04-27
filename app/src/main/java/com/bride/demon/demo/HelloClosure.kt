package com.bride.demon.demo

/**
 * <p>Created by shixin on 2019-04-27.
 */
fun main() {
    println("double(3) = ${double(3)}")

    val bb: Array<Int> = Array(5) {0}
    bb[0] = 1; bb[2] = 3; bb[4] = 5
    // named arguments
    read(b = bb)

    var byte: Byte = 9
    val bytes: Array<Byte> = Array(3){ 3.toByte() }
    bytes[1] = byte
    read(bytes)

    val list = asList("a", "b", "c")
    println("list = $list")
    // spread operator
    val arr = arrayOf('a', 'b', 'c')
    println("${asList('0', *arr, 'd', 'e')}")

    val varList: MutableList<String> = MutableList(3) {"Good"}
    varList[1] = "Better"; varList[2] = "Best"
    varList.swap(0, 2)
    println(varList)

    println(StringCollection().build().value)

    createCar()

    println("findFixPoint() ${findFixPoint()}; eps = $eps")

    val value = 1.0E-2 * 10
    println("value $value")
}

fun double(i: Int) = i * 2

// default arguments
fun <T> read(b: Array<T>, offset: Int = 0, len: Int = b.size) {
    for (i in offset until len) {
        print("i = ${b[i]}, ")
    }
    println()
}

// variable number of arguments
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (item in ts)
        result.add(item)
    return result
}

// extensions
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}

// infix notation
class StringCollection {

    var value: String = ""

    infix fun add(str: String) {
        value += str
    }

    fun build(): StringCollection {
        this.add("abc")
        this add "def"
        return this
    }
}

// local functions
fun createCar() {
    println("createCar")
    val tyreCount = 4
    val tyreColor = "red"
    fun createTyre() {
        println("createTyre $tyreColor")
    }
    for (i in 0 until tyreCount)
        createTyre()
}

// tail recursive functions
const val eps = 1E-10
tailrec fun findFixPoint(x: Double = 1.0): Double
    = if (Math.abs(x - Math.cos(x)) < eps) x else findFixPoint(Math.cos(x))