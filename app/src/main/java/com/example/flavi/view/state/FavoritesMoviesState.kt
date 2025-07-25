package com.example.flavi.view.state

import com.example.flavi.model.domain.entity.MovieCard

sealed interface FavoritesMoviesState {
    data object InitialState: FavoritesMoviesState

    data class LoadFavoritesMovies(val movieCard: List<MovieCard>): FavoritesMoviesState
}