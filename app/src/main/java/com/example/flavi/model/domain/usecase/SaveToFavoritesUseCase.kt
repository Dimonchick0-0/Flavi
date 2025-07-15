package com.example.flavi.model.domain.usecase

import com.example.flavi.model.domain.entity.Movie
import com.example.flavi.model.domain.entity.MovieCard
import com.example.flavi.model.domain.repository.UserRepository
import javax.inject.Inject

class SaveToFavoritesUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(movieCard: MovieCard) {
        repository.saveToFavorites(movieCard)
    }
}