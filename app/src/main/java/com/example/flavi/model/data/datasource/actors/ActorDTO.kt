package com.example.flavi.model.data.datasource.actors

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorDTO(
    @SerialName("staffId") val staffId: Int,
    @SerialName("nameRu") val nameRu: String,
    @SerialName("nameEn") val nameEn: String,
    @SerialName("description") val description: String?,
    @SerialName("posterUrl") val posterUrl: String,
    @SerialName("professionText") val professionText: String,
    @SerialName("professionKey") val professionKey: ProfessionActor
)

enum class ProfessionActor {
    DIRECTOR,

    ACTOR,

    PRODUCER,

    VOICE_DIRECTOR,

    TRANSLATOR,

    DESIGN,

    WRITER,

    OPERATOR,

    COMPOSER,

    EDITOR,
}
