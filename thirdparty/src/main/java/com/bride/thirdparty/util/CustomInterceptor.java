package com.bride.thirdparty.util;

import com.bride.baselib.SystemStrategy;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>Created by shixin on 2019/4/4.
 */
public class CustomInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder().addHeader("deviceId", SystemStrategy.getDeviceId()).build();
        Response response = chain.proceed(newRequest);
        return response;
    }
}
