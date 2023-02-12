package com.bride.thirdparty.coroutines.advanced

import com.bride.baselib.log
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun main() {
    /*CompletableFuture.supplyAsync {
        3
    }.thenAccept {
        log(it)
    }*/
    val result = CompletableFuture.supplyAsync {
        3
    }.await()
    log(result)
}

suspend fun <T> CompletableFuture<T>.await(): T {
    if (isDone) {
        try {
            return get()
        } catch (e: ExecutionException) {
            throw e.cause ?: e
        }
    }

    return suspendCancellableCoroutine { continuation ->
        continuation.invokeOnCancellation { cancel(true) }

        whenComplete { value, throwable ->
            if (throwable == null) {
                continuation.resume(value)
            } else {
                continuation.resumeWithException(throwable.cause ?: throwable)
            }
        }
    }
}