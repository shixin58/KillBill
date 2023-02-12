package com.bride.thirdparty.coroutines.advanced

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.bride.baselib.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun main() {
    Looper.prepareMainLooper()
    GlobalScope.launch {
        val handler = Handler(Looper.getMainLooper())
        val result = handler.run { "Hello" }
        val delayedResult = handler.runDelay({ "World" }, 1000L)
        log("$result $delayedResult")
        Looper.getMainLooper().quit()
    }
    Looper.loop()
}

suspend fun <T> Handler.run(block: () -> T): T = suspendCoroutine { continuation ->
    post {
        try {
            continuation.resume(block.invoke())
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }
}

suspend fun <T> Handler.runDelay(block: () -> T, delayMillis: Long): T = suspendCancellableCoroutine {
    cancellableContinuation ->
    val message = Message.obtain(this) {
        try {
            cancellableContinuation.resume(block.invoke())
        } catch (e: Exception) {
            cancellableContinuation.resumeWithException(e)
        }
    }.also { it.obj = cancellableContinuation }

    cancellableContinuation.invokeOnCancellation {
        // 参数token即Message.obj
        this@runDelay.removeCallbacksAndMessages(cancellableContinuation)
    }

    // postDelayed()内部调用sendMessageDelayed()
    sendMessageDelayed(message, delayMillis)
}