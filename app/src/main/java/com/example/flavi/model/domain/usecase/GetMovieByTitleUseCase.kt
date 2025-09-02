package com.example.flavi.model.domain.usecase

import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Movies
import com.example.flavi.model.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class GetMovieByTitleUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(query: String): Response<Movies> {
        return userRepository.getMovieByTitle(query)
    }
}