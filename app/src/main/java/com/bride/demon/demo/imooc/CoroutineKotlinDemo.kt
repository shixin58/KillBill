package com.bride.demon.demo.imooc

import kotlinx.coroutines.delay
import kotlin.coroutines.*

fun main() {
    // 标准库序列生成器
    val seq = sequence {
        // yield后，懒序列挂起
        yield(1)
        yield(2)
        yield(3)
        yieldAll(listOf(4, 5, 6))
    }

    // for-each loop，Java需要实现Iterable接口，Kotlin仅需定义operator fun iterator()。
    // hasNext()触发resume()
    for (x in seq) {
        println(x)
    }

    suspend {// 类型为suspend () -> Unit
        // 调用suspend fun后，主调用流程挂起1秒
        delay(1000L)
    }.startCoroutine(object : Continuation<Unit> {
        override val context: CoroutineContext = EmptyCoroutineContext

        override fun resumeWith(result: Result<Unit>) {}
    })

    val nums = generator<Int> { start ->
        for (i in 0..5) {
            yield(start + i)
        }
    }

    val sequence = nums(10)

    for (i in sequence) {
        println(i)
    }
}

interface Generator<T> {
    operator fun iterator(): Iterator<T>
}

class GeneratorImpl<T>(private val block: suspend GeneratorScope<T>.(T) -> Unit, private val param: T): Generator<T> {
    override fun iterator(): Iterator<T> {
        return GeneratorIterator(block, param)
    }
}

sealed class State {
    class NotReady(val continuation: Continuation<Unit>): State()
    class Ready<T>(val continuation: Continuation<Unit>, val nextValue: T): State()
    object Done: State()
}

class GeneratorIterator<T>(private val block: suspend GeneratorScope<T>.(T) -> Unit, override val param: T)
    : GeneratorScope<T>(), Iterator<T>, Continuation<Any?> {
    override val context: CoroutineContext = EmptyCoroutineContext

    private var state: State

    init {
        val coroutineBlock: suspend GeneratorScope<T>.() -> Unit = { block(param) }
        val start = coroutineBlock.createCoroutine(this, this)
        state = State.NotReady(start)
    }

    private fun resume() {
        when(val currentState = state) {
            is State.NotReady -> currentState.continuation.resume(Unit)
            is State.Ready<*> -> {}
            State.Done -> {}
        }
    }

    override fun hasNext(): Boolean {
        resume()
        return state != State.Done
    }

    override fun next(): T {
        return when(val currentState = state) {
            is State.NotReady -> {
                resume()
                next()
            }
            is State.Ready<*> -> {
                state = State.NotReady(currentState.continuation)
                (currentState as State.Ready<T>).nextValue
            }
            State.Done -> {
                throw IndexOutOfBoundsException("No value left.")
            }
        }
    }

    override suspend fun yield(value: T) = suspendCoroutine<Unit> { continuation ->
        state = when(state) {
            is State.NotReady -> State.Ready(continuation, value)
            State.Done -> throw IllegalStateException("Cannot yield a value when done.")
            is State.Ready<*> -> throw IllegalStateException("Cannot yield a value when ready.")
        }
    }

    override fun resumeWith(result: Result<Any?>) {
        state = State.Done
        result.getOrThrow()
    }
}

abstract class GeneratorScope<T> internal constructor() {
    protected abstract val param: T

    abstract suspend fun yield(value: T)
}

fun <T> generator(block: suspend GeneratorScope<T>.(T) -> Unit): (T) -> Generator<T> {
    return { param ->
        GeneratorImpl(block, param)
    }
}