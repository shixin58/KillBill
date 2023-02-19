package com.bride.thirdparty.coroutines.advanced

import com.bride.baselib.log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.util.concurrent.Executors

suspend fun main() {
//    flowDemo()
//    tryCollectLatest()
    tryConflate()
}

suspend fun flowDemo() {
    val dispatcher = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1)
        .asCoroutineDispatcher()
    // launch传递进来的调度器相当于RxJava里的observeOn，指定事件消费线程。
    GlobalScope.launch(dispatcher) {
        // flow类似Java8里的stream。
        val intFlow = flow<Int> {
            emit(1)
            delay(1000L)
            emit(2)
            emit(3)
        }
        // Flow#flowOn()类似RxJava里的Observable#subscribeOn()，指定事件发出线程。
        // collect类似RxJava里的subscribe方法。
        intFlow.flowOn(Dispatchers.IO)
            .collect { log(it) }
    }.join()
    dispatcher.close()
}

fun createFlow() {
    listOf(1, 2, 3, 4).asFlow()
    setOf(1, 2, 3, 4).asFlow()
    flowOf(1, 2, 3, 4)

    val channel = Channel<Int>()
    channel.consumeAsFlow()
}

fun tryChannelFlow() {
    // 不要在flow内部切换线程，因为emit非线程安全。
    // 必须切换线程的话使用channelFlow和send，因为channelFlow是并发安全的消息通道。
    channelFlow<Int> {
        send(1)
        withContext(Dispatchers.IO) {
            send(2)
        }
    }
}

suspend fun tryCollectLatest() {
    // Stream流(RxJava/Sequence/集合框架)存在的Back Pressure背压问题：发射太快、消费太慢。
    // 三种选择：buffer/conflate(保留最新值)/collectLatest。
    flow<Int> {
        emit(1)
        delay(50L)
        emit(2)
    }.collectLatest { value ->
        log("Collecting $value")
        delay(100L)
        log("Collected $value")
    }
}

suspend fun tryConflate() {
    val flow = flow<Int> {
        for (i in 1..30) {
            delay(100L)
            emit(i)
        }
    }
    val result = flow.conflate()
        .onEach {
            log(it)
            delay(1000L)
        }
        .toList()
    log("toList: $result")// 1, 10, 20, 30
}