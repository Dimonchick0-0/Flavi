package com.example.flavi.model.domain.entity.kinopoiskUnOfficial

import com.example.flavi.model.data.datasource.CountriesDTO
import com.example.flavi.model.data.datasource.GenresDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetail(
    val id: Int = UNKNOWN_MOVIE_ID,
    @SerialName("kinopoiskId") val kinopoiskId: Int = UNKNOWN_ID,
    @SerialName("nameRu") val nameRu: String = UNKNOWN_NAME,
    @SerialName("nameOriginal") val nameOriginal: String = UNKNOWN_ALTERNATIVE_NAME,
    @SerialName("posterUrl") val posterUrl: String = UNKNOWN_POSTER,
    @SerialName("ratingKinopoisk") val ratingKinopoisk: Float = UNKNOWN_RATING_KINOPOISK,
    @SerialName("ratingImdb") val ratingImdb: Float = UNKNOWN_RATING_IMDB,
    @SerialName("year") val year: Int = UNKNOWN_YEAR,
    @SerialName("description") val description: String = UNKNOWN_DESCRIPTION,
    @SerialName("genres") val genres: List<GenresDTO> = UNKNOWN_GENRES,
    @SerialName("countries") val countries: List<CountriesDTO> = UNKNOWN_COUNTRIES,
    @SerialName("startYear") val startYear: Int = UNKNOWN_START_YEAR,
    @SerialName("endYear") val endYear: Int = UNKNOWN_END_YEAR,
    @SerialName("type") val type: String = UNKNOWN_TYPE,
    @SerialName("isFavorite") val isFavorite: Boolean = UNKNOWN_IS_FAVORITE
) {
    companion object {
        private const val UNKNOWN_MOVIE_ID = 0
        private const val UNKNOWN_ID = 0
        private const val UNKNOWN_NAME = ""
        private const val UNKNOWN_DESCRIPTION = ""
        private const val UNKNOWN_ALTERNATIVE_NAME = ""
        private const val UNKNOWN_YEAR = 0
        private const val UNKNOWN_POSTER = ""
        private const val UNKNOWN_TYPE = ""
        private const val UNKNOWN_RATING_KINOPOISK = 0.0f
        private const val UNKNOWN_START_YEAR = 0
        private const val UNKNOWN_END_YEAR = 0
        private const val UNKNOWN_RATING_IMDB = 0.0f
        private val UNKNOWN_GENRES = emptyList<GenresDTO>()
        private val UNKNOWN_COUNTRIES = emptyList<CountriesDTO>()
        private const val UNKNOWN_IS_FAVORITE = false
    }
}
