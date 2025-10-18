package com.example.flavi.model.domain.entity.kinopoiskUnOfficial

data class SimilarMovie(
    val filmId: Int,
    val nameRu: String,
    val nameEn: String?,
    val posterUrlPreview: String
)
