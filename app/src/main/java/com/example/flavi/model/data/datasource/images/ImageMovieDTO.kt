package com.example.flavi.model.data.datasource.images

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageMovieDTO(
    @SerialName("items") val items: List<PosterDTO>
)
