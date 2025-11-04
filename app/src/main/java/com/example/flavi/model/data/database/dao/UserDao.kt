package com.example.flavi.model.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.flavi.model.data.database.entitydb.HistorySearchDb
import com.example.flavi.model.data.database.entitydb.MovieDetailDb
import com.example.flavi.model.data.database.entitydb.MoviesDbModel
import com.example.flavi.model.data.database.entitydb.UserDbModel
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieCard
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserToDB(userDbModel: UserDbModel)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieToDb(movies: MoviesDbModel)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetailToDb(movieDetailDb: MovieDetailDb)

    @Query("SELECT * FROM userdbmodel WHERE password ==:passwordUser AND email ==:emailUser ")
    suspend fun getUserByPasswordAndEmail(passwordUser: String, emailUser: String): UserDbModel

    @Query("SELECT EXISTS(SELECT * FROM userdbmodel WHERE email =:emailUser AND password =:passwordUser)")
    suspend fun checkUserByEmailAndPassword(emailUser: String, passwordUser: String): Boolean

    @Query("select exists(select * from movies where filmId =:movieId and userMovieId =:userId)")
    suspend fun checkMovieByTitle(movieId: Int, userId: String): Boolean

    @Query("select * from movie_detail where movieId ==:movieId")
    suspend fun getMovieDetail(movieId: Int): MovieDetailDb

    @Query("select exists(select * from movie_detail where movieId =:movieId)")
    suspend fun checkIfThereIsMovieInDb(movieId: Int): Boolean

    @Query("select userId from userdbmodel where userId =:userId")
    fun getUserId(userId: String): Long

    @Query("select * from movies where userMovieId =:userId")
    fun getFavoriteMovie(userId: String): Flow<List<MovieCard>>

    @Query("delete from movies where filmId =:movieId")
    suspend fun removeMovieFromDatabase(movieId: Int)

    @Query("delete from movie_detail")
    suspend fun removeMovieDetail()

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTitleMovieInToDatabase(title: HistorySearchDb)

    @Query("select * from historySearch where userId =:userId")
    fun getHistorySearch(userId: String): Flow<List<HistorySearchDb>>

    @Query("delete from historySearch where title ==:title")
    suspend fun removeHistorySearchBiId(title: String)
}