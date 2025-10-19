package com.example.flavi.view.state

import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieCard

sealed interface FavoriteScreenState {
    data class LoadMovies(
        val movieList: List<MovieCard>
    ): FavoriteScreenState

    data class EmptyList(
        val movieList: List<MovieCard>
    ): FavoriteScreenState
}