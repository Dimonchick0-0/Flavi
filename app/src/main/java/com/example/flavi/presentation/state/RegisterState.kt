package com.example.flavi.presentation.state

sealed interface RegisterState {
    data object FinishedRegister : RegisterState

    data class Input(
        val name: String = "",
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = ""
    ) : RegisterState
}