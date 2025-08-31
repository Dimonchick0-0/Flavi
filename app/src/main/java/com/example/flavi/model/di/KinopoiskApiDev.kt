package com.example.flavi.model.di

import com.example.flavi.model.data.datasource.KinoposikService
import com.example.flavi.model.data.datasource.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object KinopoiskApiDev {

    private const val BASE_URL = "https://api.kinopoisk.dev/"
    private const val HEADER_API = "X-API-KEY"
    private const val API_KEY = "04VGND6-Y58MEZ1-JT1CGJE-6W3ETFG"
    private const val ACCEPT = "accept"
    private const val APP_JSON = "application/json"

    @Provides
    fun provideOkHttpClientAndMovieService(): KinoposikService {
        val okHttpClient = NetworkModule.provideOkHttpClient(
            name1 = ACCEPT,
            value1 = APP_JSON,
            name2 = HEADER_API,
            value2 = API_KEY
        )

        val movieService = NetworkModule.provideMovieKinopoisk(
            service = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
                .create(KinoposikService::class.java)
        )
        return movieService
    }

}