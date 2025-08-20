package com.example.flavi.model.data.datasource

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CountriesDTO(@SerialName("country") val country: String)
