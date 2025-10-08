package com.example.flavi.model.data.datasource.rating

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingDTO(@SerialName("imdb") val imdb: Float)
