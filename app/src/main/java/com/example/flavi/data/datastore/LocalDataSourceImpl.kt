package com.example.flavi.data.datastore

import com.example.flavi.data.database.dao.UserDao

class LocalDataSourceImpl(private val userDao: UserDao) : LocalDataSource {
    override suspend fun insertUserToDb(name: String, password: String) {
        userDao.insertUserToDB(name = name, password = password)
    }
}