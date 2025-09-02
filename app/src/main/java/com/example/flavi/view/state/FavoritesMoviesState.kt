package com.example.flavi.view.state

import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieCard

sealed interface FavoritesMoviesState {
    data object InitialState: FavoritesMoviesState

    data class LoadFavoritesMovies(val movieCard: List<MovieCard>): FavoritesMoviesState
}