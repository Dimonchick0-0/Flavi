package com.example.flavi.model.data.datasource.reviews

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewDTO(
    @SerialName("type") val type: ReviewTypeDTO,
    @SerialName("date") val date: String,
    @SerialName("positiveRating") val positiveRating: Int,
    @SerialName("negativeRating") val negativeRating: Int,
    @SerialName("author") val author: String,
    @SerialName("title") val title: String?,
    @SerialName("description") val description: String
)

@Serializable
enum class ReviewTypeDTO {
    NOT_SELECTED,
    @SerialName("POSITIVE") POSITIVE,
    @SerialName("NEGATIVE") NEGATIVE,
    @SerialName("NEUTRAL") NEUTRAL
}