package com.example.flavi.model.data.datasource.services

import com.example.flavi.model.data.datasource.actors.ActorDTO
import com.example.flavi.model.data.datasource.actors.ListActor
import com.example.flavi.model.data.datasource.actors.PersonDTO
import com.example.flavi.model.data.datasource.awards.AwardsListDTO
import com.example.flavi.model.data.datasource.filter.FilterMoviesDTO
import com.example.flavi.model.data.datasource.images.ImageMovieDTO
import com.example.flavi.model.data.datasource.rental.Rental
import com.example.flavi.model.data.datasource.reviews.ReviewListDTO
import com.example.flavi.model.data.datasource.sequelsandprequels.MoviesSequelAndPrequelDTO
import com.example.flavi.model.data.datasource.similars.SimilarsDTO
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

    @GET("/api/v2.2/films/{id}/images?page=1")
    suspend fun loadImageFilmById(
        @Path("id") id: Int,
        @Query("type") type: String
    ): Response<ImageMovieDTO>

    @GET("/api/v1/staff")
    suspend fun getActorByMovieId(
        @Query("filmId") filmId: Int
    ): Response<List<ActorDTO>>

    @GET("/api/v1/persons?page=1")
    suspend fun getSearchActor(
        @Query("name") nameActor: String
    ): Response<ListActor>

    @GET("/api/v2.2/films/{id}/distributions")
    suspend fun getRentalMovie(
        @Path("id") id: Int
    ): Response<Rental>

    @GET("/api/v2.2/films/{id}/awards")
    suspend fun getAwardsMovieByMovieId(
        @Path("id") id: Int
    ): Response<AwardsListDTO>

    @GET("/api/v2.2/films/{id}/reviews?page=1&order=DATE_ASC")
    suspend fun getReviewsMovieById(
        @Path("id") id: Int
    ): Response<ReviewListDTO>

    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getMovieSimilars(
        @Path("id") id: Int
    ): Response<SimilarsDTO>

    @GET("/api/v2.1/films/{id}/sequels_and_prequels")
    suspend fun getSequelsAndPrequelsMovieById(
        @Path("id") id: Int
    ): Response<List<MoviesSequelAndPrequelDTO>>

    @GET("/api/v2.2/films?page=1")
    suspend fun getMovieByFilter(
        @Query("order") order: String,
        @Query("type") type: String,
        @Query("keyword") keyword: String
    ): Response<FilterMoviesDTO>

    @GET("/api/v1/staff/{id}")
    suspend fun getPersonDetail(
        @Path("id") id: Int
    ): Response<PersonDTO>
}