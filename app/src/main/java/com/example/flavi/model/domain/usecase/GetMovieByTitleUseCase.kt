package com.example.flavi.model.domain.usecase

import com.example.flavi.model.domain.entity.Movies
import com.example.flavi.model.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class GetMovieByTitleUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(page: Int, limit: Int, query: String): Response<Movies> {
        return userRepository.getMovieByTitle(page, limit, query)
    }
}