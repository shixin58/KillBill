package com.bride.thirdparty.coroutines.advanced

import com.bride.baselib.log
import com.bride.baselib.net.Urls
import com.bride.thirdparty.strategy.RetrofitKtStrategy
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <T> Call<T>.await(): T = suspendCancellableCoroutine { continuation ->
    continuation.invokeOnCancellation { cancel() }
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            response.takeIf { it.isSuccessful }?.body()?.also { continuation.resume(it) }
                ?:continuation.resumeWithException(HttpException(response))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            continuation.resumeWithException(t)
        }
    })
}

suspend fun main() {
    GlobalScope.launch {
        val model = RetrofitKtStrategy.mService.getPhoneInfo("13701116418", Urls.JUHE_KEY).await()
        log(model.result)
    }.cancelAndJoin()
}