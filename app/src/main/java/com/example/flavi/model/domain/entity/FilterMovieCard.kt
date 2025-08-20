package com.example.flavi.model.domain.entity

import com.example.flavi.model.data.datasource.CountriesDTO
import com.example.flavi.model.data.datasource.GenresDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilterMovieCard(
    @SerialName("kinopoiskId") val kinopoiskId: Int = UNKNOWN_ID,
    @SerialName("nameRu") val nameRu: String = UNKNOWN_NAME,
    @SerialName("nameOriginal") val nameOriginal: String = UNKNOWN_ALTERNATIVE_NAME,
    @SerialName("year") val year: String = UNKNOWN_YEAR,
    @SerialName("posterUrlPreview") val posterUrlPreview: String = UNKNOWN_POSTER,
    @SerialName("ratingImdb") val ratingImdb: Float = UNKNOWN_RATING,
    @SerialName("genres") val genres: List<GenresDTO> = UNKNOWN_GENRES,
    @SerialName("countries") val countries: List<CountriesDTO> = UNKNOWN_COUNTRIES,
    @SerialName("isFavorite") val isFavorite: Boolean = UNKNOWN_IS_FAVORITE
) {
    companion object {
        private const val UNKNOWN_ID = 0
        private const val UNKNOWN_NAME = ""
        private const val UNKNOWN_ALTERNATIVE_NAME = ""
        private const val UNKNOWN_YEAR = ""
        private const val UNKNOWN_POSTER = ""
        private const val UNKNOWN_RATING = 0.0f
        private val UNKNOWN_GENRES = emptyList<GenresDTO>()
        private val UNKNOWN_COUNTRIES = emptyList<CountriesDTO>()
        private const val UNKNOWN_IS_FAVORITE = false
    }
}
