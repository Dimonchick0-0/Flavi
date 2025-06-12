package com.example.flavi.data.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserToDB(name: String, password: String)
}