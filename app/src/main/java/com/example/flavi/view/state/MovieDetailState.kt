package com.example.flavi.view.state

import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Actor
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail

sealed interface MovieDetailState {
    data class LoadMovieDetail(
        val movie: MovieDetail,
        val actors: List<Actor>
    ) : MovieDetailState
}