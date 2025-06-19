package com.example.flavi.data.repository

import android.content.Context
import com.example.flavi.data.database.database.AppDatabase
import com.example.flavi.data.database.entitydb.UserDbModel
import com.example.flavi.domain.entity.User
import com.example.flavi.domain.repository.UserRepository

class UserRepositoryImpl private constructor(context: Context): UserRepository {
    private val appDatabase = AppDatabase.getInstance(context)
    private val userDao = appDatabase.userDao()

    override suspend fun userRegister(name: String, password: String, email: String) {
        val userDbModel = UserDbModel(
            id = 0,
            name = name,
            email = email,
            password = password
        )
        userDao.insertUserToDB(userDbModel)
    }

    override suspend fun authUser(password: String, email: String): User {
        TODO()
    }

    companion object {
        private val lock = Any()

        private var instance: UserRepositoryImpl? = null

        fun getInstance(context: Context): UserRepositoryImpl {
            instance?.let { return it }

            synchronized(lock) {
                instance?.let { return it }

                return UserRepositoryImpl(context).also { instance = it }
            }
        }
    }
}