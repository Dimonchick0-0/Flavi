package com.example.flavi.data.datasource

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiFactory {

    private const val BASE_URL = "https://api.kinopoisk.dev/"
    private const val HEADER_API = "X-API-KEY"
    private const val API_KEY = "04VGND6-Y58MEZ1-JT1CGJE-6W3ETFG"
    private const val ACCEPT = "accept"
    private const val APP_JSON = "application/json"

    val interceptor = HttpLoggingInterceptor()

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader(ACCEPT, APP_JSON)
                .addHeader(HEADER_API, API_KEY)
                .build()
            chain.proceed(request)
        }
        .addInterceptor(interceptor = interceptor.apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val movieService: MovieService = retrofit.create()
}