package com.example.flavi.model.data.datasource.kinopoisk

import com.example.flavi.model.data.datasource.countries.CountriesDTOKinopoisk
import com.example.flavi.model.data.datasource.genres.GenresDTOKinopoisk
import com.example.flavi.model.data.datasource.images.PosterDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieFiltersDTO(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("alternativeName") val alternativeName: String,
    @SerialName("type") val type: MovieTypeDTO,
    @SerialName("poster") val poster: PosterDTO,
    @SerialName("genres") val genres: List<GenresDTOKinopoisk>,
    @SerialName("countries") val countries: List<CountriesDTOKinopoisk>
)

@Serializable
enum class MovieTypeDTO {
    @SerialName("movie") MOVIE,
    @SerialName("tv-series") TV_SERIES,
    @SerialName("cartoon") CARTOON,
    @SerialName("animated-series") ANIMATED_SERIES,
    @SerialName("anime") ANIME
}
