package com.example.flavi.model.domain.entity

data class User(
    val id: Int,
    val name: String,
    val password: String,
    val email: String,
    val listMovie: MoviesCard? = null
)
