package com.example.flavi.view.state

sealed interface ProfileState {
    data class Initial(
        val nameUser: String,
        val emailUser: String
    ) : ProfileState
}