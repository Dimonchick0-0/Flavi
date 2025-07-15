package com.example.flavi.view.state

import com.example.flavi.model.data.datasource.CountriesDTO
import com.example.flavi.model.data.datasource.GenresDTO
import com.example.flavi.model.data.datasource.PosterDTO
import com.example.flavi.model.data.datasource.RatingDTO
import com.example.flavi.model.domain.entity.Movie
import com.example.flavi.model.domain.entity.MovieCard

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

    //    data class LoadMovie(
//        val id: Int,
//        val name: String,
//        val alternativeName: String,
//        val year: Int,
//        val posterDTO: PosterDTO,
//        val ratingDTO: RatingDTO,
//        val genresDTO: GenresDTO,
//        val countriesDTO: CountriesDTO
//    ) : Searcr    ``hMovieState
    data class LoadMovie(
        val movie: MovieCard
    ) : SearchMovieState
}