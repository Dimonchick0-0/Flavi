package com.example.flavi.model.data.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.flavi.model.data.database.converter.FlaviConverter
import com.example.flavi.model.data.database.dao.UserDao
import com.example.flavi.model.data.database.entitydb.HistorySearchDb
import com.example.flavi.model.data.database.entitydb.MoviesDbModel
import com.example.flavi.model.data.database.entitydb.UserDbModel

@Database(
    entities = [
        UserDbModel::class,
        MoviesDbModel::class,
        HistorySearchDb::class
    ],
    version = 41,
    exportSchema = false
)
@TypeConverters(FlaviConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}