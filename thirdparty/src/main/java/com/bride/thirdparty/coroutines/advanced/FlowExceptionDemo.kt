package com.bride.thirdparty.coroutines.advanced

import com.bride.baselib.log
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion

suspend fun main() {
//    flowException()
    tryRunCatching()
}

suspend fun flowException() {
    flow<Int> {
        // 不要在flow内部try-catch。
        emit(1)
        throw ArithmeticException("Divided by zero")
    }.catch { t ->
        log("caught error $t")
    }.onCompletion { t ->
        log("finally")
    }.collect {
        log(it)
    }
}

fun tryRunCatching() {
    runCatching<Int> {
        throw ArithmeticException("Divided by zero")
    }.onSuccess {
        log("onSuccess $it")
    }.onFailure {
        log("onFailure $it")
    }
}