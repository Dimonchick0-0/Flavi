package com.example.flavi.model.data.database.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.flavi.model.data.database.converter.FlaviConverter
import com.example.flavi.model.data.database.dao.UserDao
import com.example.flavi.model.data.database.entitydb.HistorySearchDb
import com.example.flavi.model.data.database.entitydb.MovieDetailDb
import com.example.flavi.model.data.database.entitydb.MoviesDbModel
import com.example.flavi.model.data.database.entitydb.UserDbModel

@Database(
    entities = [
        UserDbModel::class,
        MoviesDbModel::class,
        HistorySearchDb::class,
        MovieDetailDb::class
    ],
    version = 48,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(
            from = 47,
            to = 48
        )
    ]
)
@TypeConverters(FlaviConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}