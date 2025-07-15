package com.example.flavi.model.data.database.converter

import androidx.room.TypeConverter
import com.example.flavi.model.domain.entity.Movie
import com.example.flavi.model.domain.entity.MovieCard
import com.example.flavi.model.domain.entity.Movies
import com.example.flavi.model.domain.entity.MoviesCard
import com.google.gson.Gson
import java.util.Arrays

class FlaviConverter {

    @TypeConverter
    fun convertMovieListToString(movies: MoviesCard): String {
        return Gson().toJson(movies)
    }

    @TypeConverter
    fun convertStringToMovieList(string: String): MoviesCard {
        return Gson().fromJson(string, MoviesCard::class.java)
    }

}