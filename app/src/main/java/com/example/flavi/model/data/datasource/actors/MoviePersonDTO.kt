package com.example.flavi.model.data.datasource.actors

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviePersonDTO(
    @SerialName("filmId") val filmId: Int,
    @SerialName("nameRu") val nameRu: String?,
    @SerialName("nameEn") val nameEn: String?,
    @SerialName("rating") val rating: String?,
    @SerialName("professionKey") val professionKey: ProfessionKeyDTO
)

@Serializable
enum class ProfessionKeyDTO {
    NOT_SELECTED,

    @SerialName("OPERATOR") OPERATOR,

    @SerialName("EDITOR") EDITOR,

    @SerialName("DIRECTOR") DIRECTOR,

    @SerialName("WRITER") WRITER,

    @SerialName("ACTOR") ACTOR,

    @SerialName("HIMSELF") HIMSELF,

    @SerialName("HERSELF") HERSELF,

    @SerialName("PRODUCER") PRODUCER,

    @SerialName("HRONO_TITR_MALE") HRONO_TITR_MALE,

    @SerialName("HRONO_TITR_FEMALE") HRONO_TITR_FEMALE
}