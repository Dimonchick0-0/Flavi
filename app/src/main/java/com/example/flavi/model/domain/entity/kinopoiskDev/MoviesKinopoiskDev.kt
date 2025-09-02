package com.example.flavi.model.domain.entity.kinopoiskDev

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesKinopoiskDev(
    @SerialName("docs") val docs: List<MovieCardKinopoisk>
)
