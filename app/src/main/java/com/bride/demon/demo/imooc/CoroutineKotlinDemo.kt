package com.bride.demon.demo.imooc

import com.bride.baselib.dispatcher.DispatcherContext
import kotlinx.coroutines.delay
import java.util.Date
import java.util.concurrent.Executor
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

    // 3）仿Lua协程实现非对称协程(挂起恢复后，调度权还给挂起点线程)
    val producer = Coroutine.create<Unit,Int>(Dispatcher()) {
        for (i in 0..3) {
            log("send $i")
            yield(i)
        }
        200
    }
    val consumer = Coroutine.create<Int,Unit>(Dispatcher()) { param ->
        log("start $param")
        for (i in 0..3) {
            val value = yield(Unit)
            log("receive $value")
        }
    }
    while (producer.isActive && consumer.isActive) {
        // 执行block, 把值yield出去，producer挂起
        val result = producer.resume(Unit)
        // 执行block，拿到值，consumer挂起
        consumer.resume(result)
    }

    // 4）基于非对称协程实现对称协程
    SymCoroutine.main {
        println("main 0")
        val result = transfer(SymCoroutines.coroutine2, 3)
        println("main end $result")
    }

    // 5）仿Go-Channel实现协程通信
    val channel = SimpleChannel<Int>()

    go("producer") {
        for (i in 0..6) {
            log("send $i")
            channel.send(i)
        }
    }
    go("consumer", channel::close) {
        for (i in 0..5) {
            log("receive")
            val got = channel.receive()
            log("got $got")
        }
    }
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

    suspend fun <SymT> SymCoroutine<SymT>.yield(result: R): P {
        return body.yield(result)
    }
}

/** 拦截Continuation */
class Dispatcher : ContinuationInterceptor {
    override val key = ContinuationInterceptor

    // 创建出来的线程不是幽灵线程，会一直在后台运行
    private val executor = Executors.newSingleThreadExecutor()

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return DispatcherContinuation(continuation, executor)
    }
}

/** 线程切换，使用Executor */
class DispatcherContinuation<T>(val continuation: Continuation<T>, val executor: Executor) : Continuation<T> by continuation {

    override fun resumeWith(result: Result<T>) {
        // 不关注结果，所以不需要submit()
        executor.execute {
            continuation.resumeWith(result)
        }
    }
}

class SymCoroutine<T>(
    override val context: CoroutineContext = EmptyCoroutineContext,
    private val block: suspend SymCoroutine<T>.SymCoroutineBody.(T) -> Unit
) : Continuation<T> {
    companion object {
        lateinit var main: SymCoroutine<Any?>

        suspend fun main(block: suspend SymCoroutine<Any?>.SymCoroutineBody.() -> Unit) {
            // 启动调度中心协程Main
            SymCoroutine<Any?>{ block() }.also { main = it }.start(Unit)
        }

        fun <T> create(context: CoroutineContext = EmptyCoroutineContext,
                       block: suspend SymCoroutine<T>.SymCoroutineBody.(T) -> Unit): SymCoroutine<T> {
            return SymCoroutine(context, block)
        }
    }

    inner class SymCoroutineBody {
        private tailrec suspend fun <P> transferInner(symCoroutine: SymCoroutine<P>, value: Any?): T {// 尾递归优化
            // 先把自己挂起。先判断自己是不是调度中心，即Main。
            if (this@SymCoroutine.isMain) {
                return if (symCoroutine.isMain) {
                    value as T
                } else {
                    val parameter = symCoroutine.coroutine.resume(value as P)
                    transferInner(parameter.coroutine, parameter.value)
                }
            } else {
                this@SymCoroutine.coroutine.run {
                    return yield(Parameter(symCoroutine, value as P))
                }
            }
        }

        suspend fun <P> transfer(symCoroutine: SymCoroutine<P>, value: P): T {
            // Kotlin强类型，所以Any
            return transferInner(symCoroutine, value)
        }
    }

    class Parameter<T>(val coroutine: SymCoroutine<T>, val value: T)

    val isMain: Boolean
        get() = this == main

    private val body = SymCoroutineBody()

    private val coroutine = Coroutine<T,Parameter<*>>(context) {
        Parameter(this@SymCoroutine, suspend {
            block(body, it)
            // 对称协程不能执行完，除非Main
            if (this@SymCoroutine.isMain) Unit else throw IllegalStateException("SymCoroutine cannot be dead!")
        }() as T)
    }

    override fun resumeWith(result: Result<T>) {
        throw IllegalStateException("SymCoroutine cannot be dead!")
    }

    suspend fun start(value: T) {
        coroutine.resume(value)
    }
}

/** 定义了三个对等的协程 */
object SymCoroutines {
    val coroutine0: SymCoroutine<Int> = SymCoroutine.create { param ->
        println("coroutine-0 $param")
        var result = transfer(coroutine2, 0)
        println("coroutine-0 1 $result")
        result = transfer(SymCoroutine.main, Unit)
        println("coroutine-0 2 $result")
    }

    val coroutine1: SymCoroutine<Int> = SymCoroutine.create { param ->
        println("coroutine-1 $param")
        val result = transfer(coroutine0, 1)
        println("coroutine-1 1 $result")
    }

    val coroutine2: SymCoroutine<Int> = SymCoroutine.create { param ->
        println("coroutine-2 $param")
        var result = transfer(coroutine1, 2)
        println("coroutine-2 1 $result")
        result = transfer(coroutine0, 2)
        println("coroutine-2 2 $result")
    }
}

/**
 * 用不了android Log API。因此封装了println，便于查看时间和线程
 */
fun log(msg: Any) {
    println("${Date()} ${Thread.currentThread().name} $msg")
}

interface Channel<T> {
    suspend fun send(value: T)

    suspend fun receive(): T

    fun close()
}

class ClosedException(msg: String) : Exception(msg)

class SimpleChannel<T> : Channel<T> {
    sealed class Element {
        override fun toString(): String {
            // Object#getClass()
            return this.javaClass.simpleName
        }

        object None : Element()// 没发送没接收的状态
        class Producer<T>(val value: T, val continuation: Continuation<Unit>) : Element()
        class Consumer<T>(val continuation: Continuation<T>) : Element()
        object Closed : Element()
    }

    private val state = AtomicReference<Element>(Element.None)

    override suspend fun send(value: T) = suspendCoroutine<Unit> { continuation ->
        // 状态机状态流转
        val pre = state.getAndUpdate {
            when(it) {
                Element.Closed -> throw IllegalStateException("Can't send after closed.")
                is Element.Consumer<*> -> Element.None
                Element.None -> Element.Producer(value, continuation)
                is Element.Producer<*> -> throw IllegalStateException("Can't send new element while previous element isn't consumed.")
            }
        }

        (pre as? Element.Consumer<T>)?.continuation?.resume(value)?.let { continuation.resume(Unit)/*发送完恢复自己*/ }
    }

    override suspend fun receive(): T = suspendCoroutine<T> { continuation ->
        val pre = state.getAndUpdate {
            when(it) {
                Element.Closed -> throw IllegalStateException("Can't receive after closed.")
                is Element.Consumer<*> -> throw IllegalStateException("Can't receive new element while previous element isn't provided.")
                Element.None -> Element.Consumer(continuation)
                is Element.Producer<*> -> Element.None
            }
        }

        (pre as? Element.Producer<T>)?.let {
            it.continuation.resume(Unit)
            continuation.resume(it.value)// 收快递的把东西拿走
        }
    }

    override fun close() {
        val prev = state.getAndUpdate { Element.Closed }
        if (prev is Element.Consumer<*>) {// 有人在等待接收
            prev.continuation.resumeWithException(ClosedException("Channel is closed."))
        } else if (prev is Element.Producer<*>) {// 有人在发送
            prev.continuation.resumeWithException(ClosedException("Channel is closed."))
        }
    }
}

fun go(name: String = "", completion: () -> Unit = {}, block: suspend () -> Unit) {
    block.startCoroutine(object : Continuation<Any> {
        override val context = DispatcherContext()

        override fun resumeWith(result: Result<Any>) {
            log("end $name $result")
            completion()
        }
    })
}