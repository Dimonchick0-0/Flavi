package com.example.flavi.model.domain.repository

import com.example.flavi.model.domain.entity.Movie
import com.example.flavi.model.domain.entity.MovieCard
import com.example.flavi.model.domain.entity.Movies
import com.example.flavi.model.domain.entity.User
import retrofit2.Response

interface UserRepository {
    suspend fun userRegister(name: String, password: String, email: String)

    suspend fun authUser(password: String, email: String): User

    suspend fun getMovieByTitle(page: Int, limit: Int, query: String): Response<Movies>

    suspend fun saveToFavorites(movieCard: MovieCard)
}