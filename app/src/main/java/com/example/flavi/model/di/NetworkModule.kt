package com.example.flavi.model.di

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Singleton
class NetworkModule(
    @ApplicationContext context: Context
) {

    private val cacheSize = (5 * 1024 * 1024).toLong()
    private val cache = Cache(context.cacheDir, cacheSize)

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
            .cache(cache)
            .build()
    }

    fun <T> provideMovieKinopoisk(service: T): T = service

}
