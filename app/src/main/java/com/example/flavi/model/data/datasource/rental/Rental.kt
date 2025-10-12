package com.example.flavi.model.data.datasource.rental

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rental(@SerialName("items") val items: Set<RentalMovie>)
