package com.example.flavi.model.domain.entity.kinopoiskUnOfficial

data class MoviePerson(
    val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val rating: String?,
    val professionKey: ProfessionKey
)

enum class ProfessionKey {
    NOT_SELECTED,

    OPERATOR,

    EDITOR,

    DIRECTOR,

    WRITER,

    ACTOR,

    HIMSELF,

    HERSELF,

    PRODUCER,

    HRONO_TITR_MALE,

    HRONO_TITR_FEMALE
}