package com.example.flavi.model.domain.usecase

import com.example.flavi.model.domain.repository.UserRepository
import javax.inject.Inject

class RegistrationUserUseCase @Inject constructor (
    private val repository: UserRepository
) {
    suspend operator fun invoke(name: String, password: String, email: String) {
        repository.userRegister(name, password, email)
    }
}