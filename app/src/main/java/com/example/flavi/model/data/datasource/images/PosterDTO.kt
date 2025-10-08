package com.example.flavi.model.data.datasource.images

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PosterDTO(
    @SerialName("previewUrl") val previewUrl: String
)