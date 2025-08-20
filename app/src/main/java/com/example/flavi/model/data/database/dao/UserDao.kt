package com.example.flavi.model.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.flavi.model.data.database.entitydb.MoviesDbModel
import com.example.flavi.model.data.database.entitydb.UserDbModel
import com.example.flavi.model.data.database.map.toMoviesDbModel
import com.example.flavi.model.domain.entity.MovieCard
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserToDB(userDbModel: UserDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieToDb(movies: MoviesDbModel)

    @Query("SELECT * FROM userdbmodel WHERE password ==:passwordUser AND email ==:emailUser ")
    suspend fun getUserByPasswordAndEmail(passwordUser: String, emailUser: String): UserDbModel

    @Query("SELECT EXISTS(SELECT * FROM userdbmodel WHERE email =:emailUser AND password =:passwordUser)")
    suspend fun checkUserByEmailAndPassword(emailUser: String, passwordUser: String): Boolean

    @Query("select exists(select * from movies where filmId =:movieId and userMovieId =:userId)")
    suspend fun checkMovieByTitle(movieId: Int, userId: String): Boolean

    @Query("select userId from userdbmodel where userId =:userId")
    fun getUserId(userId: String): Long

    @Query("select * from movies where userMovieId =:userId")
    fun getFavoriteMovie(userId: String): Flow<List<MoviesDbModel>>

    @Query("delete from movies where filmId =:movieId")
    suspend fun removeMovieFromDatabase(movieId: Int)

    @Transaction
    suspend fun insertUserToDb(
        userDbModel: UserDbModel,
        movies: MoviesDbModel,
        userId: String
    ) {
        insertUserToDB(userDbModel)
        insertMovieToDb(movies)
    }
}