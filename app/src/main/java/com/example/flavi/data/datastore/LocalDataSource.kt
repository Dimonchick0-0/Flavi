package com.example.flavi.data.datastore

interface LocalDataSource {
    suspend fun insertUserToDb(name: String, password: String)
}