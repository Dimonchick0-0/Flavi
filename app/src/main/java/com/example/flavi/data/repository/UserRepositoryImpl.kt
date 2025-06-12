package com.example.flavi.data.repository

import com.example.flavi.data.datastore.LocalDataSourceImpl
import com.example.flavi.domain.repository.UserRepository

class UserRepositoryImpl(private val localDataSourceImpl: LocalDataSourceImpl) : UserRepository {
    override suspend fun userRegister(name: String, password: String) {
        localDataSourceImpl.insertUserToDb(name = name, password = password)
    }
}