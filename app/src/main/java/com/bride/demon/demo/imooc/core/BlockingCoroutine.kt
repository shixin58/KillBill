package com.bride.demon.demo.imooc.core

import com.bride.baselib.dispatcher.IDispatcher
import java.util.concurrent.LinkedBlockingDeque
import kotlin.coroutines.CoroutineContext

typealias EventTask = () -> Unit

/**
 * 生产者
 */
class BlockingQueueDispatcher : LinkedBlockingDeque<EventTask>(), IDispatcher {
    override fun dispatch(block: () -> Unit) {
        offer(block)
    }
}

/**
 * 消费者
 */
class BlockingCoroutine<T>(context: CoroutineContext, private val eventQueue: LinkedBlockingDeque<EventTask>)
    : AbstractCoroutine<T>(context) {
    fun joinBlocking(): T {
        while (!isCompleted) {
            eventQueue.take().invoke()
        }
        return (state.get() as CoroutineState.Complete<T>).let {
            it.value ?: throw it.exception!!
        }
    }
}