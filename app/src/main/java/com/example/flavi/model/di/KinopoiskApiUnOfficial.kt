package com.example.flavi.model.di

import com.example.flavi.model.data.datasource.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object KinopoiskApiUnOfficial {

    //    private const val BASE_URL = "https://api.kinopoisk.dev/"
    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/"
    private const val HEADER_API = "X-API-KEY"
    private const val API_KEY = "65e12335-5427-4b62-a715-7fef0133e98c"
    //    private const val ACCEPT = "accept"
    private const val CONTENT_TYPE = "Content-Type"
    private const val APP_JSON = "application/json"

    @Provides
    fun provideOkHttpClientAndMovieService(): MovieService {
        val okHttpClient = NetworkModule.provideOkHttpClient(
            name1 = HEADER_API,
            value1 = API_KEY,
            name2 = CONTENT_TYPE,
            value2 = APP_JSON
        )
        val movieService = NetworkModule.provideMovie(
            okHttpClient = okHttpClient,
            url = BASE_URL
        )
        return movieService
    }

}