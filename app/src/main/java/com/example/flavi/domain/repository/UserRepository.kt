package com.example.flavi.domain.repository

interface UserRepository {
    suspend fun userRegister(name: String, password: String)
}