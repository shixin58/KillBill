package com.bride.demon.demo.imooc

import kotlinx.coroutines.delay
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.*

suspend fun main() {
    // 1）标准库序列生成器Sequence
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

    // 2）仿Python Generator
    val nums = generator<Int> { start ->
        for (i in 0..5) {
            yield(start + i)
        }
    }

    val sequence = nums(10)

    for (i in sequence) {
        println(i)
    }

    // 3）仿Lua协程实现非对程协程(挂起恢复后，调度权还给挂起点线程)
    val producer = Coroutine.create<Unit,Int>(Dispatcher()) {
        for (i in 0..3) {
            println("send $i")
            yield(i)
        }
        200
    }
    val consumer = Coroutine.create<Int,Unit>(Dispatcher()) { param ->
        println("start $param")
        for (i in 0..3) {
            val value = yield(Unit)
            println("receive $value")
        }
    }
    while (producer.isActive && consumer.isActive) {
        // 执行block, 把值yield出去，producer挂起
        val result = producer.resume(Unit)
        // 执行block，拿到值，consumer挂起
        consumer.resume(result)
    }

    // 4）基于非对程协程实现对称协程
}

interface Generator<T> {
    operator fun iterator(): Iterator<T>
}

class GeneratorImpl<T>(private val block: suspend GeneratorScope<T>.(T) -> Unit, private val param: T) : Generator<T> {
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

sealed class Status {
    class Created(val continuation: Continuation<Unit>): Status()
    class Yielded<P>(val continuation: Continuation<P>): Status()
    class Resumed<R>(val continuation: Continuation<R>): Status()
    object Dead: Status()
}

class Coroutine<P,R>(
    override val context: CoroutineContext = EmptyCoroutineContext,
    private val block: suspend Coroutine<P,R>.CoroutineBody.(P) -> R
) : Continuation<R> {

    companion object {
        fun <P,R> create(context: CoroutineContext = EmptyCoroutineContext/* 拦截器切换线程 */,
                         block: suspend Coroutine<P,R>.CoroutineBody.(P) -> R): Coroutine<P,R> {
            return Coroutine(context, block)
        }
    }

    inner class CoroutineBody {
        var param: P? = null

        suspend fun yield(result: R): P = suspendCoroutine { continuation ->
            // 状态机状态流转
            val previousStatus = status.getAndUpdate {
                when(it) {
                    is Status.Created -> throw IllegalStateException("Never started!")
                    Status.Dead -> throw IllegalStateException("Already dead!")
                    is Status.Resumed<*> -> Status.Yielded(continuation)
                    is Status.Yielded<*> -> throw IllegalStateException("Already yielded!")
                }
            }
            (previousStatus as Status.Resumed<R>).continuation.resume(result)
        }
    }

    private val body = CoroutineBody()

    private val status: AtomicReference<Status>

    val isActive: Boolean
        get() = status.get() != Status.Dead

    init {
        val coroutineBlock: suspend CoroutineBody.() -> R = { block(param!!) }
        val start = coroutineBlock.createCoroutine(body, this)
        status = AtomicReference(Status.Created(start))
    }

    override fun resumeWith(result: Result<R>) {
        // 状态机状态流转
        val previousStatus = status.getAndUpdate {
            when(it) {
                is Status.Created -> throw IllegalStateException("Never started!")
                Status.Dead -> throw IllegalStateException("Already dead!")
                is Status.Resumed<*> -> Status.Dead
                is Status.Yielded<*> -> throw IllegalStateException("Already yielded!")
            }
        }
        (previousStatus as? Status.Resumed<R>)?.continuation?.resumeWith(result)
    }

    suspend fun resume(param: P): R = suspendCoroutine { continuation ->
        // 状态机状态流转
        val previousStatus = status.getAndUpdate {// 可能执行多次
            when(it) {
                is Status.Created -> {
                    body.param = param
                    Status.Resumed(continuation)
                }
                Status.Dead -> throw IllegalStateException("Already dead!")
                is Status.Resumed<*> -> throw IllegalStateException("Already resumed!")
                is Status.Yielded<*> -> Status.Resumed(continuation)
            }
        }
        when(previousStatus) {
            is Status.Created -> previousStatus.continuation.resume(Unit)// 协程刚刚创建，resume()不需要参数
            is Status.Yielded<*> -> (previousStatus as Status.Yielded<P>).continuation.resume(param)
            else -> {}
        }
    }
}

/** 拦截Continuation */
class Dispatcher : ContinuationInterceptor {
    override val key = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return DispatcherContinuation(continuation)
    }
}

/** 线程切换，使用Executor */
class DispatcherContinuation<T>(val continuation: Continuation<T>) : Continuation<T> by continuation {
    // 创建出来的线程不是幽灵线程，会一直在后台运行
    private val executor = Executors.newSingleThreadExecutor()

    override fun resumeWith(result: Result<T>) {
        executor.submit {
            continuation.resumeWith(result)
        }
    }
}