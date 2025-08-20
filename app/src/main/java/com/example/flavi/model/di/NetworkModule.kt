package com.example.flavi.model.di

import com.example.flavi.model.data.datasource.MovieService
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
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

//    private const val BASE_URL = "https://api.kinopoisk.dev/"
    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
    private const val HEADER_API = "X-API-KEY"
    private const val API_KEY = "65e12335-5427-4b62-a715-7fef0133e98c"
//    private const val ACCEPT = "accept"
    private const val CONTENT_TYPE = "Content-Type"
    private const val APP_JSON = "application/json"

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader(HEADER_API, API_KEY)
                    .addHeader(CONTENT_TYPE, APP_JSON)
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