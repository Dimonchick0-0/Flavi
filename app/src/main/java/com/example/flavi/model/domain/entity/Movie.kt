package com.example.flavi.model.domain.entity

import com.example.flavi.model.data.datasource.PosterDTO
import com.example.flavi.model.data.datasource.RatingDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("id") val id: Int,
    @SerialName("poster") val poster: PosterDTO,
    @SerialName("name") val name: String,
    @SerialName("year") val year: Int,
    @SerialName("description") val description: String,
    @SerialName("rating") val rating: RatingDTO
)
