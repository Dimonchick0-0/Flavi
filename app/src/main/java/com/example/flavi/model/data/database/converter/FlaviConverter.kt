package com.example.flavi.model.data.database.converter

import androidx.room.TypeConverter
import com.example.flavi.model.data.datasource.actors.ActorDTO
import com.example.flavi.model.data.datasource.countries.CountriesDTO
import com.example.flavi.model.data.datasource.genres.GenresDTO
import com.example.flavi.model.data.datasource.rental.Rental
import com.example.flavi.model.data.datasource.rental.RentalMovie
import com.example.flavi.model.data.datasource.reviews.ReviewDTO
import com.example.flavi.model.data.datasource.similars.SimilarMovieDTO
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Actor
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MoviesSequelAndPrequel
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Review
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.SimilarMovie
import com.google.gson.Gson

class FlaviConverter {

    @TypeConverter
    fun fromListGenresDTOToString(list: List<GenresDTO>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToListGenresDTO(string: String): List<GenresDTO> {
        return Gson().fromJson(string, Array<GenresDTO>::class.java).toList()
    }

    @TypeConverter
    fun fromListCountriesDTOToString(list: List<CountriesDTO>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToListCountriesDTO(string: String): List<CountriesDTO> {
        return Gson().fromJson(string, Array<CountriesDTO>::class.java).toList()
    }

    @TypeConverter
    fun fromListActorToString(list: List<Actor>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToListActor(string: String): List<Actor> {
        return Gson().fromJson(string, Array<Actor>::class.java).toList()
    }

    @TypeConverter
    fun fromRentalToString(rental: Set<RentalMovie>): String {
        return Gson().toJson(rental)
    }

    @TypeConverter
    fun fromStringToRental(string: String): Set<RentalMovie> {
        return Gson().fromJson(string, Array<RentalMovie>::class.java).toSet()
    }

    @TypeConverter
    fun fromListReviewToString(list: List<Review>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToListReview(string: String): List<Review> {
        return Gson().fromJson(string, Array<Review>::class.java).toList()
    }

    @TypeConverter
    fun fromListSimilarMovieToString(list: List<SimilarMovie>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromStringToListSimilarMovie(string: String): List<SimilarMovie> {
        return Gson().fromJson(string, Array<SimilarMovie>::class.java).toList()
    }

    @TypeConverter
    fun fromMoviesSequelAndPrequelToString(moviesSequelAndPrequel: List<MoviesSequelAndPrequel>): String {
        return Gson().toJson(moviesSequelAndPrequel)
    }

    @TypeConverter
    fun fromStringToMoviesSequelAndPrequel(string: String): List<MoviesSequelAndPrequel> {
        return Gson().fromJson(string, Array<MoviesSequelAndPrequel>::class.java).toList()
    }

    @TypeConverter
    fun fromMovieDetailToString(movieDetail: MovieDetail): String {
        return Gson().toJson(movieDetail)
    }

    @TypeConverter
    fun fromStringToMovieDetail(string: String): MovieDetail {
        return Gson().fromJson(string, MovieDetail::class.java)
    }
}