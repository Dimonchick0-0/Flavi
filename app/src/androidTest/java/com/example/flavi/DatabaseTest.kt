package com.example.flavi

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.flavi.model.data.database.dao.UserDao
import com.example.flavi.model.data.database.database.AppDatabase
import com.example.flavi.model.data.database.entitydb.UserDbModel
import com.example.flavi.model.data.database.map.toMoviesDbModel
import com.example.flavi.model.domain.entity.MovieCard
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var dao: UserDao
    private lateinit var database: AppDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = database.userDao()
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun addTestUserInDatabase() {
        val user = UserDbModel(
            userId = "testid",
            name = "Test",
            password = "zzzzzz",
            email = "testjunit@mail.ru"
        )
        runBlocking {
            dao.insertUserToDB(user)
            dao.insertMovieToDb(MovieCard().toMoviesDbModel("testid"))
        }
    }

}