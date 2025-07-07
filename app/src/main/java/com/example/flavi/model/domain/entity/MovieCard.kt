package com.example.flavi.model.domain.entity

import com.example.flavi.model.data.datasource.CountriesDTO
import com.example.flavi.model.data.datasource.GenresDTO
import com.example.flavi.model.data.datasource.PosterDTO
import com.example.flavi.model.data.datasource.RatingDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCard(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("alternativeName") val alternativeName: String,
    @SerialName("year") val year: Int,
    @SerialName("poster") val poster: PosterDTO,
    @SerialName("rating") val rating: RatingDTO,
    @SerialName("genres") val genres: List<GenresDTO>,
    @SerialName("countries") val countries: List<CountriesDTO>
)
