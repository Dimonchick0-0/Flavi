package com.example.flavi.model.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object NetworkModule {

    fun <T, V, K, L> provideOkHttpClient(
        name1: T,
        value1: V,
        name2: K,
        value2: L
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader(name1 as String, value1 as String)
                    .addHeader(name2 as String, value2 as String)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    fun <T> provideMovieKinopoisk(service: T): T = service

}
