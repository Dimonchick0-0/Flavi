package com.example.flavi.model.di

import android.content.Context
import com.example.flavi.BuildConfig
import com.example.flavi.model.data.datasource.KinoposikService
import com.example.flavi.model.data.datasource.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object KinopoiskApiUnOfficial {

    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
    private const val HEADER_API = "X-API-KEY"
    private const val API_KEY = BuildConfig.FLAVI_API_KEY_UN_OFFICIAL_KINOPOISK
    private const val CONTENT_TYPE = "Content-Type"
    private const val APP_JSON = "application/json"

    @Provides
    fun provideOkHttpClientAndMovieService(
        @ApplicationContext context: Context
    ): MovieService {
        val okHttpClient = NetworkModule(context).provideOkHttpClient(
            name1 = HEADER_API,
            value1 = API_KEY,
            name2 = CONTENT_TYPE,
            value2 = APP_JSON
        )
        val movieService = NetworkModule(context).provideMovieKinopoisk(
            service = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
                .create(MovieService::class.java)
        )
        return movieService
    }

}