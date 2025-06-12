package com.example.flavi.domain

interface UserRepository {
    fun userRegister(name: String, password: String)
}