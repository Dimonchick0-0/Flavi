package com.example.flavi.model.data.repository

import com.example.flavi.model.data.database.dao.UserDao
import com.example.flavi.model.data.database.entitydb.MovieDetailDb
import com.example.flavi.model.data.database.map.toMovie
import com.example.flavi.model.data.datasource.rental.Rental
import com.example.flavi.model.data.datasource.rental.RentalMovie
import com.example.flavi.model.data.datasource.services.MovieService
import com.example.flavi.model.domain.entity.Movie
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Actor
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MoviesSequelAndPrequel
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Review
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.SimilarMovie
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun removeMovieDetail() = userDao.removeMovieDetail()

    suspend fun checkIfThereIsMovieInDb(movieId: Int): Boolean {
        return userDao.checkIfThereIsMovieInDb(movieId = movieId)
    }

    suspend fun getMovieDetail(movieId: Int): Movie {
        val movieDetailDb = userDao.getMovieDetail(movieId = movieId)
        return Movie(
            movieDetail = movieDetailDb.movieDetail,
            actors = movieDetailDb.actors,
            rentals = movieDetailDb.rentals,
            reviews = movieDetailDb.reviews,
            similars = movieDetailDb.similars,
            sequalsAndPrequals = movieDetailDb.sequalsAndPrequals
        )
    }

    suspend fun insertMovieDetailToDb(
        movieId: Int,
        movieDetail: MovieDetail,
        actors: List<Actor>,
        rentals: Set<RentalMovie>,
        reviews: List<Review>,
        similars: List<SimilarMovie>,
        sequalsAndPrequals: List<MoviesSequelAndPrequel>
    ) {
        userDao.insertMovieDetailToDb(
            MovieDetailDb(
                movieId = movieId,
                movieDetail = movieDetail,
                actors = actors,
                rentals = rentals,
                reviews = reviews,
                similars = similars,
                sequalsAndPrequals = sequalsAndPrequals
            )
        )
    }

}