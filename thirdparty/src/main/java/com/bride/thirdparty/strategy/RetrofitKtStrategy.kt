package com.bride.thirdparty.strategy

import android.util.Log
import com.bride.baselib.bean.PhoneNumberModel
import com.bride.baselib.bean.WrapperModel
import com.bride.baselib.net.Urls
import com.bride.thirdparty.protocal.IService
import com.bride.thirdparty.util.RetrofitClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import kotlin.coroutines.*

object RetrofitKtStrategy {
    val mService: IService = RetrofitClient.getRetrofit().create(IService::class.java)

    fun getPhoneInfo() {
        MainScope().launch {
            val wrapperModel = mService.getPhoneInfoSuspend("13701116418", Urls.JUHE_KEY)
            Log.i("RetrofitKtStrategy", "getPhoneInfo ${wrapperModel.result}")
        }
    }

    fun getPhoneInfo2() {
        GlobalScope.launch {
            val wrapperModel = mService.getPhoneInfoSuspend("13701116418", Urls.JUHE_KEY)
            Log.i("RetrofitKtStrategy", "getPhoneInfo2 ${wrapperModel.result}")
        }
    }

    fun testSuspend() {
        suspend {// SuspendLambda
            // 1）调用suspend fun(挂起点)，主调用流程挂起
            Log.i("RetrofitKtStrategy", "testSuspend 开始执行协程逻辑")
            val wrapperModel = mService.getPhoneInfoSuspend("13701116418", Urls.JUHE_KEY)
            // 2）suspend fun返回，恢复主调用流程，但未回到调用前的协程(主线程)
            Log.i("RetrofitKtStrategy", "testSuspend ${wrapperModel.result}")
            wrapperModel
        }.startCoroutine(object : Continuation<WrapperModel<PhoneNumberModel>> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<WrapperModel<PhoneNumberModel>>) {
                // 3）协程逻辑结束，执行completion#resumeWith()
                Log.i("RetrofitKtStrategy", "testSuspend resumeWith $result")
            }
        })
    }

    fun testSuspend2() {
        suspend {// suspend () -> Unit
            // 2）调用suspend fun(挂起点)，主调用流程挂起
            // SafeContinuation#delegate包装SuspendLambda
            Log.i("RetrofitKtStrategy", "testSuspend2 开始执行协程逻辑")
            val result = getNameSuspend(1)
            // 4）suspend fun返回，恢复主调用流程
            Log.i("RetrofitKtStrategy", "testSuspend2 $result")
            result
        }.createCoroutine(object : Continuation<String> {
            override val context: CoroutineContext
                get() = EmptyCoroutineContext

            override fun resumeWith(result: Result<String>) {
                // 5）协程逻辑结束，执行CompletedContinuation#resumeWith()
                Log.i("RetrofitKtStrategy", "testSuspend2 resumeWith $result")
            }
        }).resumeWith(Result.success(Unit))// 1）调用协程本体SafeContinuation#resume()启动协程，Result为value class(代替inline class)
    }

    // 编译后的class字节码反编译会追加Continuation参数，通过suspendCoroutine拿到SafeContinuation
    private suspend fun getNameSuspend(id: Int) = suspendCoroutine<String> { continuation ->
        // 模拟线程切换。真正的挂起指切换到其他线程再resume(返回正常结果) or resumeWithException(返回异常)，返回COROUTINE_SUSPENDED。
        thread {
            // 3）执行resume后，外围suspend函数恢复
            Log.i("RetrofitKtStrategy", "getNameSuspend2 线程切换并执行耗时逻辑")
            continuation.resumeWith(Result.success("Max - $id"))
        }
    }
}