package com.example.flavi.model.domain.entity.kinopoiskUnOfficial

data class Awards(
    val name: String = UNKNOWN_NAME,
    val nominationName: String = UNKNOWN_NOMINATION_NAME,
    val year: Int = UNKNOWN_YEAR,
    val persons: List<SearchActor?> = UNKNOWN_PERSONS
) {
    companion object {
        private const val UNKNOWN_NAME = ""
        private const val UNKNOWN_NOMINATION_NAME = ""
        private const val UNKNOWN_YEAR = 0
        private val UNKNOWN_PERSONS = listOf<SearchActor?>()
    }
}
