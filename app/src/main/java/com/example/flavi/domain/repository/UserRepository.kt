package com.example.flavi.domain.repository

interface UserRepository {
    fun userRegister(name: String, password: String)
}