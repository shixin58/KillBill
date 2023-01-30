package com.bride.thirdparty.strategy

import android.os.Handler
import android.os.Looper
import com.bride.baselib.dispatcher.DispatcherContext
import com.bride.baselib.dispatcher.IDispatcher
import com.bride.baselib.net.Urls
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.*

fun main() {
    // 6）仿JavaScript实现async/await, 给Kotlin官方Deferred打基础
    Looper.prepare()
    val handlerDispatcher = DispatcherContext(object : IDispatcher {
        val handler = Handler(Looper.myLooper()!!)
        override fun dispatch(block: () -> Unit) {
            handler.post(block)
        }
    })
    async(handlerDispatcher) {
        val wrapperModel = await { RetrofitKtStrategy.mService.getPhoneInfo("13701116418", Urls.JUHE_KEY) }
        println("${Thread.currentThread().name} ${wrapperModel.result}")
    }
    Looper.loop()
}

interface AsyncScope

suspend fun <T> AsyncScope.await(block: () -> Call<T>) = suspendCoroutine<T> { continuation ->
    val call = block()
    call.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {// [200,300)
                response.body()?.let { continuation.resume(it) }?:continuation.resumeWithException(NullPointerException())
            } else {
                continuation.resumeWithException(HttpException(response))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            continuation.resumeWithException(t)
        }
    })
}

fun async(context: CoroutineContext = EmptyCoroutineContext, block: suspend AsyncScope.() -> Unit) {
    val completion = AsyncCoroutine(context)
    block.startCoroutine(completion, completion)
}

class AsyncCoroutine(override val context: CoroutineContext = EmptyCoroutineContext) : Continuation<Unit>, AsyncScope {
    override fun resumeWith(result: Result<Unit>) {
        result.getOrThrow()
    }
}