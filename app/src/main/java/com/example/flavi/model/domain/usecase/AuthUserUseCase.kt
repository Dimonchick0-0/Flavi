package com.example.flavi.model.domain.usecase

import com.example.flavi.model.domain.entity.User
import com.example.flavi.model.domain.repository.UserRepository
import javax.inject.Inject

class AuthUserUseCase @Inject constructor (private val repository: UserRepository) {
    suspend operator fun invoke(password: String, email: String): User {
        return repository.authUser(password, email)
    }
}