package com.example.flavi.model.data.database.entitydb

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.flavi.model.data.datasource.CountriesDTO
import com.example.flavi.model.data.datasource.GenresDTO

@Entity(
    tableName = "movies",
    primaryKeys = ["userMovieId"],
    foreignKeys = [
        ForeignKey(
            entity = UserDbModel::class,
            parentColumns = ["userId"],
            childColumns = ["userMovieId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MoviesDbModel(
    val userMovieId: String = UNKNOWN_USER_MOVIE_ID,
    val movieId: Int,
    val name: String,
    val alternativeName: String,
    val poster: String,
    val year: Int,
    val rating: Float,
    val genres: List<GenresDTO>,
    val countries: List<CountriesDTO>,
    val isFavorite: Boolean
) {
    companion object {
        private const val UNKNOWN_USER_MOVIE_ID = ""
    }
}
