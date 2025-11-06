package com.example.flavi.view.state

sealed interface ProfileState {
    data class RegisteredUser(
        val nameUser: String,
        val emailUser: String
    ) : ProfileState

    data object Initial: ProfileState

    data object NotRegisterUser: ProfileState
}