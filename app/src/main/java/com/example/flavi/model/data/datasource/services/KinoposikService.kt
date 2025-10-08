package com.example.flavi.model.data.datasource.services

import com.example.flavi.model.domain.entity.kinopoiskDev.MoviesKinopoiskDev
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KinoposikService {

    @GET("/v1.4/movie/search")
    suspend fun getListMovie(
        @Query("query") query: String
    ): Response<MoviesKinopoiskDev>

}