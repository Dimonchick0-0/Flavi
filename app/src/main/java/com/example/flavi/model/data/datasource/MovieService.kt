package com.example.flavi.model.data.datasource

import com.example.flavi.model.domain.entity.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("/v1.4/movie/search")
    suspend fun getMovieByQuery(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("query") query: String
    ): Response<Movies>
}