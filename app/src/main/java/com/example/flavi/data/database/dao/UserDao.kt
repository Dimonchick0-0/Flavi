package com.example.flavi.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flavi.data.database.entitydb.UserDbModel

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserToDB(userDbModel: UserDbModel)

    @Query("SELECT * FROM userdbmodel WHERE password ==:passwordUser AND email ==:emailUser ")
    suspend fun getUserByPasswordAndEmail(passwordUser: String, emailUser: String): UserDbModel

    @Query("SELECT EXISTS(SELECT * FROM userdbmodel WHERE email =:emailUser AND password =:passwordUser)")
    suspend fun checkUserByEmailAndPassword(emailUser: String, passwordUser: String): Boolean

}