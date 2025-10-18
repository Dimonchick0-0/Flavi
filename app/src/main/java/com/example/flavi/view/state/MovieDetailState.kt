package com.example.flavi.view.state

import com.example.flavi.model.data.datasource.rental.RentalMovie
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Actor
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MoviesSequelAndPrequel
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Review
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.SimilarMovie

sealed interface MovieDetailState {
    data class LoadMovieDetail(
        val movie: MovieDetail,
        val actors: List<Actor>,
        val rental: Set<RentalMovie>,
        val review: List<Review>,
        val similars: List<SimilarMovie>,
        val sequelsAndPreques: List<MoviesSequelAndPrequel>
    ) : MovieDetailState
}