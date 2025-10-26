package com.example.flavi.model.data.datasource.filter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilterMoviesDTO(@SerialName("items") val items: List<FilterMovieDTO>)
