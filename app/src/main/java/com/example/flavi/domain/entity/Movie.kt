package com.example.flavi.domain.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("year") val year: Int,
    @SerialName("description") val description: String
)
