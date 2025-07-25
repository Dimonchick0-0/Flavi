package com.example.flavi.view.state

import com.example.flavi.model.domain.entity.MovieCard
import com.example.flavi.model.domain.entity.Movies

sealed interface SearchMovieState {
    data object Initial : SearchMovieState

    data object NotFound : SearchMovieState

    data object NetworkShutdown : SearchMovieState

    data class NotificationOfInternetLoss(
        val notification: String
    ) : SearchMovieState

    data object ConnectToTheNetwork : SearchMovieState

    data class InputQuery(
        val query: String
    ) : SearchMovieState

    data class LoadMovie(
        val movie: MovieCard
    ) : SearchMovieState

    data class SwitchingFiltersState(val filter: String): SearchMovieState

    data class LoadListMovieWithFilters(val listMovie: Movies): SearchMovieState
}