package com.example.flavi.model.domain.entity

import com.example.flavi.model.data.datasource.rental.RentalMovie
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Actor
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MoviesSequelAndPrequel
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Review
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.SimilarMovie

data class Movie(
    val movieDetail: MovieDetail,
    val actors: List<Actor>,
    val rentals: Set<RentalMovie>,
    val reviews: List<Review>,
    val similars: List<SimilarMovie>,
    val sequalsAndPrequals: List<MoviesSequelAndPrequel>
)
