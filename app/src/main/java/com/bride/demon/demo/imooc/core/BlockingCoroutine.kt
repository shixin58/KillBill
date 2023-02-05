package com.bride.demon.demo.imooc.core

import com.bride.baselib.dispatcher.IDispatcher
import java.util.concurrent.LinkedBlockingDeque
import kotlin.coroutines.CoroutineContext

typealias EventTask = () -> Unit

/**
 * 生产者。LinkedBlockingDeque继承BlockingQueue。
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
            // waiting if necessary until an element becomes available
            eventQueue.take().invoke()
        }
        return (state.get() as CoroutineState.Complete<T>).let {
            it.value ?: throw it.exception!!
        }
    }
}