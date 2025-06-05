package com.example.flavi.domain.usecase

import com.example.flavi.domain.repository.UserRepository

class AuthUserUseCase(private val repository: UserRepository) {
    operator fun invoke(email: String, password: String) {
        repository.authUser(email = email, password = password)
    }
}