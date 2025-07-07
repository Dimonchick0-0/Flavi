package com.example.flavi.model.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movies(
    @SerialName("docs") val docs: List<MovieCard>
)
