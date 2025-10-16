package com.example.flavi.model.data.datasource.reviews

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewListDTO(
    @SerialName("total") val total: Int,
    @SerialName("totalPositiveReviews") val totalPositiveReviews: Int,
    @SerialName("totalNegativeReviews") val totalNegativeReviews: Int,
    @SerialName("totalNeutralReviews") val totalNeutralReviews: Int,
    @SerialName("items") val items: List<ReviewDTO>
)