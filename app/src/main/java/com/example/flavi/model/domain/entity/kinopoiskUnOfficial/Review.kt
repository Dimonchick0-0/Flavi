package com.example.flavi.model.domain.entity.kinopoiskUnOfficial

data class Review(
    val type: ReviewType,
    val date: String,
    val positiveRating: Int,
    val negativeRating: Int,
    val author: String,
    val title: String?,
    val description: String
)

enum class ReviewType {
    NOT_SELECTED,
    POSITIVE,
    NEGATIVE,
    NEUTRAL
}
