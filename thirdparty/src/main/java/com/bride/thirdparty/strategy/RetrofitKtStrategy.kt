package com.bride.thirdparty.strategy

import android.util.Log
import com.bride.baselib.net.Urls
import com.bride.thirdparty.protocal.IService
import com.bride.thirdparty.util.RetrofitClient
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

object RetrofitKtStrategy {
    private var mService: IService = RetrofitClient.getRetrofit().create(IService::class.java)

    fun getPhoneInfo() {
        MainScope().launch {
            val wrapperModel = mService.getPhoneInfo3("13701116418", Urls.JUHE_KEY)
            Log.i("RetrofitKtStrategy", "getPhoneInfo ${wrapperModel.result}")
        }
    }

    fun getPhoneInfo2() {
        GlobalScope.launch {
            val wrapperModel = mService.getPhoneInfo3("13701116418", Urls.JUHE_KEY)
            Log.i("RetrofitKtStrategy", "getPhoneInfo2 ${wrapperModel.result}")
        }
    }
}