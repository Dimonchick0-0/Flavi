package com.example.flavi.model.data.datasource.awards

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AwardsListDTO(@SerialName("items") val items: List<AwardsDTO>)
