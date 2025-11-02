package com.example.flavi.model.domain.entity.kinopoiskUnOfficial

data class Person(
    val personId: Int = UNKNOWN_ID,
    val nameRu: String = UNKNOWN_NAME_RU,
    val nameEn: String = UNKNOWN_NAME_EN,
    val sex: SEX = SEX.MALE,
    val posterUrl: String = UNKNOWN_POSTER_URL,
    val birthday: String = UNKNOWN_BIRTHDAY,
    val death: String? = UNKNOWN_DEATH,
    val age: Int = UNKNOWN_AGE,
    val birthplace: String = UNKNOWN_BIRTHPLACE,
    val deathplace: String? = UNKNOWN_DEATHPLACE,
    val profession: String = UNKNOWN_PROFESSION,
    val films: List<MoviePerson> = emptyList()
) {
    companion object {
        private const val UNKNOWN_ID = 0
        private const val UNKNOWN_NAME_RU = ""
        private const val UNKNOWN_NAME_EN = ""
        private const val UNKNOWN_POSTER_URL = ""
        private const val UNKNOWN_BIRTHDAY = ""
        private val UNKNOWN_DEATH = null
        private const val UNKNOWN_AGE = 0
        private const val UNKNOWN_BIRTHPLACE = ""
        private const val UNKNOWN_DEATHPLACE = ""
        private const val UNKNOWN_PROFESSION = ""
    }
}

enum class SEX { MALE, FEMALE }