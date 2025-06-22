package com.example.flavi.domain.usecase

import com.example.flavi.domain.entity.User
import com.example.flavi.domain.repository.UserRepository

class AuthUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(password: String, email: String): User {
        return repository.authUser(password, email)
    }
}