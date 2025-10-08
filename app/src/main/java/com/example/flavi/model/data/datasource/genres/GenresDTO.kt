package com.example.flavi.model.data.datasource.genres

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresDTO(@SerialName("genre") val genre: String)
