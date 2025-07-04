package com.example.flavi.view.state

sealed interface AuthUserState {
    data object Finished: AuthUserState

    data class AuthUser(
        val password: String = "",
        val email: String = ""
    ): AuthUserState
}