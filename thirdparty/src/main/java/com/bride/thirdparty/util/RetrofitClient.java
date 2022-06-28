package com.bride.thirdparty.util;

import com.bride.baselib.net.Urls;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.lang.ref.SoftReference;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 弱引用保存Retrofit单例
 * <p>Created by shixin on 2019/4/8.
 */
public class RetrofitClient {
    private static SoftReference<Retrofit> mRetrofitSR;

    public static Retrofit getRetrofit() {
        if (mRetrofitSR == null || mRetrofitSR.get() == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Urls.JUHE_HOST)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mRetrofitSR = new SoftReference<>(retrofit);
        }
        return mRetrofitSR.get();
    }
}
