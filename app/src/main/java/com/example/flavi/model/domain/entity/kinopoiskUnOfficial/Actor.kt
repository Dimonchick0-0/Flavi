package com.example.flavi.model.domain.entity.kinopoiskUnOfficial

import com.example.flavi.model.data.datasource.actors.ProfessionActor

data class Actor(
    val staffId: Int,
    val nameRu: String,
    val nameEn: String,
    val description: String?,
    val posterUrl: String,
    val professionText: String,
    val professionKey: ProfessionActor
)

