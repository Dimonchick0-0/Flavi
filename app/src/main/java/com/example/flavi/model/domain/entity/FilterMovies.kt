package com.example.flavi.model.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilterMovies(
    @SerialName("items") val items: List<FilterMovieCard>
)
