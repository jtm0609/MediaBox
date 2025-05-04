package com.example.remote.api.interceptor

import com.example.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RequestHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request()
            .newBuilder()
            .apply {
                addHeader("Authorization", BuildConfig.REST_API_KEY)
            }
            .build()
        return chain.proceed(newRequest)
    }
}