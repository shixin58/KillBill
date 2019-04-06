package com.bride.thirdparty.util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * <p>Created by shixin on 2019/4/4.
 */
public class CustomNetworkInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originResponse = chain.proceed(chain.request());
        return originResponse.newBuilder().removeHeader("pragma")/* pragma: no-cache */
                .header("Cache-Control", "max-age=60").build();
    }
}
