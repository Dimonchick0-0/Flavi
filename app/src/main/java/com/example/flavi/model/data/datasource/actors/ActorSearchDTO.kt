package com.example.flavi.model.data.datasource.actors

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorSearchDTO(
    @SerialName("kinopoiskId")val kinopoiskId: Int,
    @SerialName("nameRu")val nameRu: String,
    @SerialName("nameEn")val nameEn: String,
    @SerialName("posterUrl")val posterUrl: String,
)
