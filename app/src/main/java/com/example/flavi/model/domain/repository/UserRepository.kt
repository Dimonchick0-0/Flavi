package com.example.flavi.model.domain.repository

import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Movies
import com.example.flavi.model.domain.entity.User
import retrofit2.Response

interface UserRepository {
    suspend fun userRegister(name: String, password: String, email: String)

    suspend fun authUser(password: String, email: String): User

    suspend fun getMovieByTitle(query: String): Response<Movies>

    suspend fun removeMovieFromFavorite(movieId: Int)
}