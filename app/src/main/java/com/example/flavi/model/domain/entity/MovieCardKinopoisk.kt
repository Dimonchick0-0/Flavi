package com.example.flavi.model.domain.entity

import com.example.flavi.model.data.datasource.CountriesDTO
import com.example.flavi.model.data.datasource.GenresDTOKinopoisk
import com.example.flavi.model.data.datasource.PosterDTO
import com.example.flavi.model.data.datasource.RatingDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCardKinopoisk(
    @SerialName("id") val id: Int = UNKNOWN_ID,
    @SerialName("name") val name: String = UNKNOWN_NAME,
    @SerialName("alternativeName") val alternativeName: String = UNKNOWN_ALTERNATIVE_NAME,
    @SerialName("year") val year: Int = UNKNOWN_YEAR,
    @SerialName("poster") val poster: PosterDTO = UNKNOWN_POSTER,
    @SerialName("rating") val rating: RatingDTO = UNKNOWN_RATING,
    @SerialName("genres") val genres: List<GenresDTOKinopoisk> = UNKNOWN_GENRES,
    @SerialName("countries") val countries: List<CountriesDTO> = UNKNOWN_COUNTRIES,
    @SerialName("isFavorite") val isFavorite: Boolean = UNKNOWN_IS_FAVORITE
) {
    companion object {
        private const val UNKNOWN_ID = 0
        private const val UNKNOWN_NAME = ""
        private const val UNKNOWN_ALTERNATIVE_NAME = ""
        private const val UNKNOWN_YEAR = 0
        private val UNKNOWN_POSTER: PosterDTO = PosterDTO(previewUrl = "")
        private val UNKNOWN_RATING: RatingDTO = RatingDTO(0.0f)
        private val UNKNOWN_GENRES = emptyList<GenresDTOKinopoisk>()
        private val UNKNOWN_COUNTRIES = emptyList<CountriesDTO>()
        private const val UNKNOWN_IS_FAVORITE = false
    }
}
