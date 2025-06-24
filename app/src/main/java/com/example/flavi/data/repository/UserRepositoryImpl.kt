package com.example.flavi.data.repository

import com.example.flavi.data.database.dao.UserDao
import com.example.flavi.data.database.entitydb.UserDbModel
import com.example.flavi.data.database.map.toEntity
import com.example.flavi.domain.entity.User
import com.example.flavi.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao ): UserRepository {

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
        return userDao.getUserByPasswordAndEmail(password, email).toEntity()
    }

    suspend fun checkUser(email: String, password: String): Boolean {
        return userDao.checkUserByEmailAndPassword(email, password)
    }

}