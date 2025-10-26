package com.example.flavi.model.data.datasource.filter

import com.example.flavi.model.data.datasource.countries.CountriesDTO
import com.example.flavi.model.data.datasource.genres.GenresDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilterMovieDTO(
    val movieId: Int,
    @SerialName("kinopoiskId") val kinopoiskId: Int,
    @SerialName("nameRu") val nameRu: String,
    @SerialName("nameOriginal") val nameOriginal: String?,
    @SerialName("genres") val genres: List<GenresDTO>,
    @SerialName("countries") val countries: List<CountriesDTO>,
    @SerialName("year") val year: Int,
    @SerialName("type") val type: String,
    @SerialName("posterUrlPreview") val posterUrlPreview: String,
    @SerialName("ratingImdb") val ratingImdb: Float?
)