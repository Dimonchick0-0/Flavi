package com.example.flavi.presentation.state

sealed interface AuthUserState {
    data object Finished: AuthUserState

    data class AuthUser(
        val password: String = "",
        val email: String = ""
    ): AuthUserState
}