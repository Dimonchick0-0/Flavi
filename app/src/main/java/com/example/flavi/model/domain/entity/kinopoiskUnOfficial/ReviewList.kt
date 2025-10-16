package com.example.flavi.model.domain.entity.kinopoiskUnOfficial

data class ReviewList(
    val total: Int = UNKNOWN_TOTAL,
    val totalPositiveReviews: Int = UNKNOWN_TOTAL_POS,
    val totalNegativeReviews: Int = UNKNOWN_TOTAL_NEG,
    val totalNeutralReviews: Int = UNKNOWN_TOTAL_NEUT,
    val items: List<Review> = reviews
) {
    companion object {
        private const val UNKNOWN_TOTAL = 0
        private const val UNKNOWN_TOTAL_POS = 0
        private const val UNKNOWN_TOTAL_NEG = 0
        private const val UNKNOWN_TOTAL_NEUT = 0
        private val reviews = listOf<Review>()
    }
}
