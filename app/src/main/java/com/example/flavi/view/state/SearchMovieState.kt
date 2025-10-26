package com.example.flavi.view.state

import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.FilterMovie
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieCard
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.SearchActor

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

    data class LoadMovieAndActors(
        val movie: List<MovieCard>,
        val searchActor: SearchActor
    ) : SearchMovieState

    data object GenresMovie: SearchMovieState

    data class LoadListMovieWithFilters(val listMovie: List<FilterMovie>): SearchMovieState
}