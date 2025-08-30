package com.example.flavi.model.data.datasource

import com.example.flavi.model.domain.entity.FilterMovies
import com.example.flavi.model.domain.entity.MovieDetail
import com.example.flavi.model.domain.entity.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("/api/v2.1/films/search-by-keyword")
    suspend fun getListMoviesByQuery(
        @Query("keyword") keyword: String
    ): Response<Movies>

    @GET("/api/v2.2/films?order=RATING&type=FILM&ratingFrom=0&" +
            "ratingTo=10&yearFrom=1000&yearTo=3000&page=1")
    suspend fun getFilterListMovies(
        @Query("keyword") keyword: String,
        @Query("type") type: String
    ): Response<FilterMovies>

    @GET("/api/v2.2/films/{id}")
    suspend fun getMovieById(
        @Path("id") id: Int
    ): Response<MovieDetail>
}