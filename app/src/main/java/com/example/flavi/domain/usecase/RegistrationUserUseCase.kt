package com.example.flavi.domain.usecase

import com.example.flavi.domain.repository.UserRepository

class RegistrationUserUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(name: String, password: String) {
        repository.userRegister(name, password)
    }
}