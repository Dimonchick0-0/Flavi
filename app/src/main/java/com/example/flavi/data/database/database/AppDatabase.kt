package com.example.flavi.data.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flavi.data.database.dao.UserDao
import com.example.flavi.data.database.entitydb.UserDbModel

@Database(
    entities = [UserDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private val lock = Any()

        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            instance?.let { return it }

            synchronized(lock) {
                instance?.let { return it }

                return Room.databaseBuilder(
                    context = context,
                    klass = AppDatabase::class.java,
                    name = "user.db"
                ).build().also { instance = it }
            }
        }
    }
}