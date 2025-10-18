package com.example.flavi.model.data.datasource.similars

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SimilarsDTO(@SerialName("items") val items: List<SimilarMovieDTO>)
