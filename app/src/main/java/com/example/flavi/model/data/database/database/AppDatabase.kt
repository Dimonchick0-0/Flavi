package com.example.flavi.model.data.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flavi.model.data.database.dao.UserDao
import com.example.flavi.model.data.database.entitydb.UserDbModel

@Database(
    entities = [UserDbModel::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}