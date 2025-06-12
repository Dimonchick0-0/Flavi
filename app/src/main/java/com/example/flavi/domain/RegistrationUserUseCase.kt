package com.example.flavi.domain

class RegistrationUserUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(name: String, password: String) {
        repository.userRegister(name, password)
    }
}