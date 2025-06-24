package com.example.flavi.domain.usecase

import com.example.flavi.domain.repository.UserRepository
import javax.inject.Inject

class RegistrationUserUseCase @Inject constructor (
    private val repository: UserRepository
) {
    suspend operator fun invoke(name: String, password: String, email: String) {
        repository.userRegister(name, password, email)
    }
}