package com.example.flavi.view.state

import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail

sealed interface MovieDetailState {
    data class LoadMovieDetail(val movie: MovieDetail) : MovieDetailState
}