package com.example.flavi.model.domain.entity.kinopoiskUnOfficial

data class MoviesSequelAndPrequel(
    val filmId: Int,
    val nameRu: String,
    val nameEn: String,
    val posterUrlPreview: String,
    val relationsType: RelationsType?
)

enum class RelationsType {
    SEQUEL,
    PREQUEL
}
