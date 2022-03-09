package com.example.flow.data.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    private val movieApiKey = "=390c98f7"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val modifiedRequestUrl = chain
            .request()
            .url
            .toString()
            .replaceFirst("=apikey", movieApiKey)

        val modifiedRequest = originalRequest.newBuilder()
            .url(modifiedRequestUrl)
            .build()

        return chain.proceed(modifiedRequest)
    }
}