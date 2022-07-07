package com.bride.thirdparty.protocal

import retrofit2.http.GET
import com.bride.baselib.bean.WrapperModel
import com.bride.baselib.bean.PhoneNumberModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.http.Query

/**
 *
 * Created by shixin on 2018/9/20.
 */
interface IService {
    @GET("mobile/get")
    fun getPhoneInfo(
        @Query("phone") phone: String,
        @Query("key") key: String
    ): Call<WrapperModel<PhoneNumberModel>>

    @GET("mobile/get")
    fun getPhoneInfo2(
        @Query("phone") phone: String,
        @Query("key") key: String
    ): Observable<WrapperModel<PhoneNumberModel>>

    // 编译后追加参数Continuation<WrapperModel<PhoneNumberModel>>
    @GET("mobile/get")
    suspend fun getPhoneInfoSuspend(
        @Query("phone") phone: String,
        @Query("key") key: String
    ): WrapperModel<PhoneNumberModel>
}