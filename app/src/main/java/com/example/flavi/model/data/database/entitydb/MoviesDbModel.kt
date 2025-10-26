package com.example.flavi.model.data.database.entitydb

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.flavi.model.data.datasource.countries.CountriesDTO
import com.example.flavi.model.data.datasource.genres.GenresDTO

@Entity(tableName = "movies")
data class MoviesDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userMovieId: String = UNKNOWN_USER_MOVIE_ID,
    val filmId: Int,
    val nameRu: String,
    val nameEn: String?,
    val posterUrlPreview: String,
    val year: String,
    val rating: String,
    val genres: List<GenresDTO>,
    val countries: List<CountriesDTO>,
    val isFavorite: Boolean
) {
    companion object {
        private const val UNKNOWN_USER_MOVIE_ID = ""
    }
}
