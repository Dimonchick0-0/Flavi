package com.example.flavi.model.data.datasource.similars

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimilarMovieDTO(
     @SerialName("filmId") val filmId: Int,
     @SerialName("nameRu") val nameRu: String,
     @SerialName("nameEn") val nameEn: String?,
     @SerialName("posterUrlPreview") val posterUrlPreview: String
)