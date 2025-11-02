package com.example.flavi.model.data.datasource.actors

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonDTO(
    @SerialName("personId") val personId: Int,
    @SerialName("nameRu") val nameRu: String,
    @SerialName("nameEn") val nameEn: String,
    @SerialName("sex") val sex: SEXDTO,
    @SerialName("posterUrl") val posterUrl: String,
    @SerialName("birthday") val birthday: String,
    @SerialName("death") val death: String?,
    @SerialName("age") val age: Int,
    @SerialName("birthplace") val birthplace: String,
    @SerialName("deathplace") val deathplace: String?,
    @SerialName("profession") val profession: String,
    @SerialName("films") val films: List<MoviePersonDTO>
)

@Serializable
enum class SEXDTO {
    @SerialName("MALE") MALE,
    @SerialName("FEMALE") FEMALE
}