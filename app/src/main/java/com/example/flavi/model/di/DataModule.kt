package com.example.flavi.model.di

import android.content.Context
import androidx.room.Room
import com.example.flavi.model.data.database.dao.UserDao
import com.example.flavi.model.data.database.database.AppDatabase
import com.example.flavi.model.data.datasource.Network
import com.example.flavi.model.data.repository.UserRepositoryImpl
import com.example.flavi.model.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    companion object {

        @Singleton
        @Provides
        fun provideUserDatabase(
            @ApplicationContext context: Context
        ): AppDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
                name = "user.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        @Singleton
        @Provides
        fun provideUserDao(database: AppDatabase): UserDao {
            return database.userDao()
        }

        @Singleton
        @Provides
        fun provideNetwork(
            @ApplicationContext context: Context
        ): Network {
            return Network(context)
        }
    }
}