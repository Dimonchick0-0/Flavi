package com.example.flavi.model.data.datasource

import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("/api/v2.1/films/search-by-keyword")
    suspend fun getListMoviesByQuery(
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ): Response<Movies>

    @GET("/api/v2.2/films/{id}")
    suspend fun getMovieById(
        @Path("id") id: Int
    ): Response<MovieDetail>
}