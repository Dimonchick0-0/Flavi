package com.example.flavi.model.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movies(
    @SerialName("films") val films: List<MovieCard>
)
