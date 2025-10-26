package com.example.flavi.model.data.datasource.sequelsandprequels

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesSequelAndPrequelDTO(
    @SerialName("filmId") val filmId: Int,
    @SerialName("nameRu") val nameRu: String,
    @SerialName("nameEn") val nameEn: String,
    @SerialName("posterUrlPreview") val posterUrlPreview: String,
    @SerialName("relationType") val relationType: RelationsTypeDTO?
)

@Serializable
enum class RelationsTypeDTO {
    @SerialName("SEQUEL") SEQUEL,
    @SerialName("PREQUEL") PREQUEL
}
