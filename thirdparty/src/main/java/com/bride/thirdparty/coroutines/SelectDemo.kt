package com.bride.thirdparty.coroutines

import com.bride.baselib.bean.PhoneNumberModel
import com.bride.baselib.bean.WrapperModel
import com.bride.baselib.log
import com.bride.baselib.net.Urls
import com.bride.thirdparty.strategy.RetrofitKtStrategy
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select
import java.io.File

val localDir = File("localCache").also { it.mkdirs() }

val gson = Gson()

fun CoroutineScope.getPhoneInfoFromRemoteAsync(phone: String) = async(Dispatchers.IO) {
    RetrofitKtStrategy.mService.getPhoneInfoSuspend(phone, Urls.JUHE_KEY)
}

fun CoroutineScope.getPhoneInfoFromLocalAsync(phone: String): Deferred<WrapperModel<PhoneNumberModel>?> = async(Dispatchers.IO) {
    File(localDir, phone).takeIf { it.exists() }?.readText()?.let {
        gson.fromJson(it, object : TypeToken<WrapperModel<PhoneNumberModel>>() {}.type)
    }
}

fun cachePhoneInfo(phone: String, model: WrapperModel<PhoneNumberModel>) {
    File(localDir, phone).writeText(gson.toJson(model))
}

data class Response<T>(val value: T, val isLocal: Boolean)

suspend fun main() {
    val phone = "13701116418"
    GlobalScope.launch {
        val localDeferred = getPhoneInfoFromLocalAsync(phone)
        val remoteDeferred = getPhoneInfoFromRemoteAsync(phone)

        val userResponse = select<Response<WrapperModel<PhoneNumberModel>?>> {
            localDeferred.onAwait { Response(it, true) }
            remoteDeferred.onAwait { Response(it, false) }
        }

        userResponse.value?.let { log("${userResponse.isLocal} ${it.result}") }
        userResponse.isLocal.takeIf { it }?.let {
            val modelFromRemote = remoteDeferred.await()
            cachePhoneInfo(phone, modelFromRemote)
            log("false ${modelFromRemote.result}")
        }
    }.join()
}