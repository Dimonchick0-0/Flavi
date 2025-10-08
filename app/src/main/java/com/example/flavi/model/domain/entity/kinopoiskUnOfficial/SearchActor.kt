package com.example.flavi.model.domain.entity.kinopoiskUnOfficial

data class SearchActor(
    val kinopoiskId: Int = UNKNOWN_ID,
    val nameRu: String = UNKNOWN_NAME_RU,
    val nameEn: String = UNKNOWN_NAME_EN,
    val posterUrl: String = UNKNOWN_POSTER,
) {
    companion object {
        private const val UNKNOWN_ID = 0
        private const val UNKNOWN_NAME_RU = ""
        private const val UNKNOWN_NAME_EN = ""
        private const val UNKNOWN_POSTER = ""
    }
}
