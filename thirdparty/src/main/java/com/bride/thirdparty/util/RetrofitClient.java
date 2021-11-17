package com.bride.thirdparty.util;

import com.bride.baselib.net.Urls;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.lang.ref.SoftReference;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>Created by shixin on 2019/4/8.
 */
public class RetrofitClient {
    private static SoftReference<Retrofit> mRetrofitSoftReference;
    public static Retrofit getRetrofit() {
        if (mRetrofitSoftReference == null || mRetrofitSoftReference.get() == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(Urls.JUHE_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
            mRetrofitSoftReference = new SoftReference<>(retrofit);
        }
        return mRetrofitSoftReference.get();
    }
}
