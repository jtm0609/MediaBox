package com.example.data.api.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor : Interceptor {
    companion object {
        private const val TAG = "NetworkLog"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        
        // 요청 로깅
        val requestLog = StringBuilder().apply {
            append("Request: ${request.method} ${request.url}\n")
            append("Headers: ${request.headers}\n")
            if (request.body != null) {
                append("Body: ${request.body}")
            }
        }
        
        Log.d(TAG, requestLog.toString())
        
        // 응답 시간 측정 시작
        val startTime = System.currentTimeMillis()
        
        // 요청 실행
        val response = chain.proceed(request)
        
        // 응답 시간 계산
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        
        // 응답 로깅
        val responseLog = StringBuilder().apply {
            append("Response: ${response.code} for ${request.url}\n")
            append("Duration: ${duration}ms\n")
            append("Headers: ${response.headers}\n")
        }
        
        Log.d(TAG, responseLog.toString())
        
        return response
    }
} 