package com.example.flavi.model.domain.usecase

import com.example.flavi.model.domain.repository.UserRepository
import javax.inject.Inject

class RemovieMovieFromFavoritesUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(movieId: Int) {
        repository.removeMovieFromFavorite(movieId)
    }

}