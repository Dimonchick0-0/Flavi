package com.example.flavi.model.data.repository

import com.example.flavi.model.data.database.dao.UserDao
import com.example.flavi.model.data.database.entitydb.UserDbModel
import com.example.flavi.model.data.database.map.toEntity
import com.example.flavi.model.data.database.map.toMoviesDbModel
import com.example.flavi.model.data.datasource.MovieService
import com.example.flavi.model.domain.entity.MovieCard
import com.example.flavi.model.domain.entity.Movies
import com.example.flavi.model.domain.entity.User
import com.example.flavi.model.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val movieService: MovieService
) : UserRepository {

    override suspend fun userRegister(name: String, password: String, email: String) {
        val userId = GetFirebaseAuth.getIdUser()
        val userDbModel = UserDbModel(
            userId = userId,
            name = name,
            email = email,
            password = password
        )
        userDao.insertUserToDB(userDbModel)
        userDao.insertMovieToDb(MovieCard().toMoviesDbModel(userId))
    }

    suspend fun checkMovieInDbByTitle(movieId: Int): Boolean {
        return userDao.checkMovieByTitle(movieId)
    }

    suspend fun saveMovieToDb(movieCard: MovieCard) {
        val id = GetFirebaseAuth.getIdUser()
        withContext(Dispatchers.IO) {
            userDao.insertMovieToDb(movieCard.toMoviesDbModel(id))
        }
    }

    override suspend fun authUser(password: String, email: String): User {
        return userDao.getUserByPasswordAndEmail(password, email).toEntity()
    }

    suspend fun checkUser(email: String, password: String): Boolean {
        return userDao.checkUserByEmailAndPassword(email, password)
    }

    override suspend fun getMovieByTitle(page: Int, limit: Int, query: String): Response<Movies> {
        return movieService.getMovieByQuery(page, limit, query)
    }

}