package com.bride.demon.demo.imooc.scope

import com.bride.demon.demo.imooc.Job
import com.bride.demon.demo.imooc.core.AbstractCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

/**
 * 作用域本质就是个上下文。
 */
interface CoroutineScope {
    val scopeContext: CoroutineContext
}

/**
 * 整合多个作用域，拼接多个协程上下文。
 */
internal class ContextScope(context: CoroutineContext): CoroutineScope {
    override val scopeContext: CoroutineContext = context
}

operator fun CoroutineScope.plus(context: CoroutineContext): CoroutineScope =
    ContextScope(scopeContext + context)

/**
 * 作用域跟协程绑定在一起，协程可以取消，作用域也可以取消。
 */
fun CoroutineScope.cancel() {
    val job = scopeContext[Job]?: error("Scope can't be cancelled because it doesn't have a job: $this")
    job.cancel()
}

/**
 * 协同作用域。方便获取当前作用域，并在内部创建新协程。
 */
suspend fun <R> coroutineScope(block: suspend CoroutineScope.() -> R): R =
    suspendCoroutine { continuation ->
        val coroutine = ScopeCoroutine(continuation.context, continuation)
        block.startCoroutine(coroutine, coroutine)
    }

/**
 * 隐式协程。每个作用域对应一个协程。
 */
internal open class ScopeCoroutine<T>(context: CoroutineContext, val continuation: Continuation<T>) :
    AbstractCoroutine<T>(context) {
    override fun resumeWith(result: Result<T>) {
        super.resumeWith(result)
        continuation.resumeWith(result)
    }
}