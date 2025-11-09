package com.example.flavi

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.flavi.model.data.database.dao.UserDao
import com.example.flavi.model.data.database.database.AppDatabase
import com.example.flavi.model.data.database.entitydb.UserDbModel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import kotlin.io.encoding.Base64
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.io.encoding.ExperimentalEncodingApi

@RunWith(AndroidJUnit4::class)
class FlaviTestDatabase: TestCase() {

    private lateinit var db: AppDatabase

    private lateinit var dao: UserDao

    @Before
    override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.userDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @OptIn(ExperimentalEncodingApi::class)
    @Test
    fun addPersonToDatabase() = runBlocking {
        val user = UserDbModel(
            userId = "1dsadwewfdfdweqw",
            name = "Димон",
            password = Base64.encode("rrrrrr".toByteArray()),
            email = "emailemail@mail.ru"
        )
        dao.insertUserToDB(user)
        val testUser = dao.getUserByPasswordAndEmail(
            passwordUser = Base64.encode("rrrrrr".toByteArray()),
            emailUser = "emailemail@mail.ru"
        )
        val listUser = mutableListOf<UserDbModel>()
        listUser.add(testUser)
        assertEquals(1, listUser.size)
    }
}