package com.example.flavi.model.data.datasource.awards

import com.example.flavi.model.data.datasource.actors.ActorSearchDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AwardsDTO(
    @SerialName("name") val name: String,
    @SerialName("nominationName") val nominationName: String,
    @SerialName("year") val year: Int,
    @SerialName("persons") val persons: List<ActorSearchDTO?>
)
