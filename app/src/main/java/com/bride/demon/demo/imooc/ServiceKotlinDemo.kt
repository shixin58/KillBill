package com.bride.demon.demo.imooc

import com.bride.baselib.bean.PhoneNumberModel
import com.bride.baselib.bean.WrapperModel
import com.bride.baselib.net.Urls
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.File

interface ServiceKotlinDemo {
    @GET("mobile/get")
    fun getPhoneInfo(@Query("phone") phone: String, @Query("key") key: String): Call<WrapperModel<PhoneNumberModel>>
}

fun main() {
    val service = Retrofit.Builder()
        .baseUrl(Urls.JUHE_HOST)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ServiceKotlinDemo::class.java)

    val model = service.getPhoneInfo("18600166830", Urls.JUHE_KEY)
        .execute()
        .body()

    // 扩展方法很清爽
    if (model != null) {
        File("phone_info.json").writeText("""
            ${model.result}
        """.trimIndent())
    }
}