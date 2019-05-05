package com.bride.demon.demo

/**
 * <p>Created by shixin on 2019-05-02.
 */
fun main() {
    val list = listOf(1, 2, 3, 5, 8)
    list.fold(0, {
        acc: Int, i: Int ->
        print("acc = $acc, i = $i, ")
        val result = acc + i
        println("result = $result")
        result
    })

    val joinedToString = list.fold("Elements: ", {acc, i -> acc + " " + i})
    println("\njoinedToString = $joinedToString")

    val product = list.fold(1, Int::times)
    println("product = $product")

    val stringPlus: (String, String) -> String = String::plus
    val intPlus: Int.(Int) -> Int = Int::plus
    println(stringPlus.invoke("\n<-", "->"))
    println(stringPlus("Hello, ", "world!"))
    println(2.intPlus(3))

    val strings = listOf("a", "abc", "ab")
    println("\nmax = ${max(strings) { a, b -> a.length < b.length}}")
    var ints = listOf(-3, 3, 5, 4, -1)
    ints = ints.filter {
        it > 0
    }
    println("ints.filter $ints")
    ints.filter {
        val shouldFilter = it > 0
        return@filter shouldFilter
    }

    val sum = fun Int.(other: Int): Int = this +other
}

fun <T, R> Collection<T>.fold(
        initial: R,
        combine: (acc: R, nextElement: T) -> R): R {
    var accumulator: R = initial
    for (element: T in this) {
        accumulator = combine(accumulator, element)
    }
    return accumulator
}

fun max(strings: List<String>, compare: (a: String, b: String) -> Boolean): String {
    var result = strings[0]
    for (i in 1 until strings.size) {
        if (compare(result, strings[i])) {
            result = strings[i]
        }
    }
    return result
}