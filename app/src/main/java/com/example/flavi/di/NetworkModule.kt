package com.example.flavi.di

import com.example.flavi.data.datasource.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://api.kinopoisk.dev/"
    private const val HEADER_API = "X-API-KEY"
    private const val API_KEY = "04VGND6-Y58MEZ1-JT1CGJE-6W3ETFG"
    private const val ACCEPT = "accept"
    private const val APP_JSON = "application/json"

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader(ACCEPT, APP_JSON)
                    .addHeader(HEADER_API, API_KEY)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    fun provideMovie(
        okHttpClient: OkHttpClient
    ): MovieService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MovieService::class.java)
    }

}