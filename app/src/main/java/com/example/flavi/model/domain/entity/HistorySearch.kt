package com.example.flavi.model.domain.entity

data class HistorySearch(
    val userId: String = UNKNOWN_USER_MOVIE_ID,
    val id: Int = UNKNOWN_ID,
    val title: String
) {
    companion object {
        private const val UNKNOWN_ID = 0
        private const val UNKNOWN_USER_MOVIE_ID = ""
    }
}
