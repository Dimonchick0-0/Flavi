package com.example.flavi.domain.repository

import com.example.flavi.data.User

interface UserRepository {
    fun authUser(email: String, password: String)
    fun registerUser(user: User)
}