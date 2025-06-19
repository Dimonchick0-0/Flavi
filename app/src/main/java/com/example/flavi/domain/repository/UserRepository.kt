package com.example.flavi.domain.repository

import com.example.flavi.domain.entity.User

interface UserRepository {
    suspend fun userRegister(name: String, password: String, email: String)

    suspend fun authUser(password: String, email: String): User
}