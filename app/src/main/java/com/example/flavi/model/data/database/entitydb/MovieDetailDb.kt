package com.example.flavi.model.data.database.entitydb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.flavi.model.data.datasource.rental.Rental
import com.example.flavi.model.data.datasource.rental.RentalMovie
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Actor
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MoviesSequelAndPrequel
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Review
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.SimilarMovie

@Entity(tableName = "movie_detail")
data class MovieDetailDb(
    @PrimaryKey
    val movieId: Int,
    val movieDetail: MovieDetail,
    val actors: List<Actor>,
    val rentals: Set<RentalMovie>,
    val reviews: List<Review>,
    val similars: List<SimilarMovie>,
    val sequalsAndPrequals: List<MoviesSequelAndPrequel>
)
