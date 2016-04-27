package com.example.onlinetyari.readablereddit.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Siddharth Verma on 27/4/16.
 */
public class CacheInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {

        return chain.proceed(chain.request())
                .newBuilder()
                .header("Cache-Control","max-age=3600 ")
                .build();
    }
}
