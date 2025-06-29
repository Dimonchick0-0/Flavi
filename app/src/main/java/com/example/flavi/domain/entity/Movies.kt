package com.example.flavi.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movies(
    @SerialName("docs") val docs: List<Movie>
)
