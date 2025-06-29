package com.example.flavi.domain.usecase

import com.example.flavi.domain.entity.Movies
import com.example.flavi.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class GetMovieByTitleUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(page: Int, limit: Int, query: String): Response<Movies> {
        return userRepository.getMovieByTitle(page, limit, query)
    }
}