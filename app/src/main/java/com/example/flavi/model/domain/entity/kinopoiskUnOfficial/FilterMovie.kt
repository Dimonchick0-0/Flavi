package com.example.flavi.model.domain.entity.kinopoiskUnOfficial

data class FilterMovie(
    val movieId: Int,
    val kinopoiskId: Int,
    val nameRu: String,
    val nameOriginal: String?,
    val genres: List<Genre>,
    val countries: List<Country>,
    val year: Int,
    val type: String,
    val posterUrlPreview: String,
    val ratingImdb: Float?
)