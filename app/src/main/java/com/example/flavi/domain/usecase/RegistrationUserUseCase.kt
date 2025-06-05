package com.example.flavi.domain.usecase

import com.example.flavi.data.User
import com.example.flavi.domain.repository.UserRepository

class RegistrationUserUseCase(private val repository: UserRepository) {
    operator fun invoke(user: User) {
        repository.registerUser(user = user)
    }
}