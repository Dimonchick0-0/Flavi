package com.example.flavi.model.data.repository

import com.example.flavi.model.data.database.dao.UserDao
import com.example.flavi.model.data.database.entitydb.MoviesDbModel
import com.example.flavi.model.data.database.entitydb.UserDbModel
import com.example.flavi.model.data.database.map.toEntity
import com.example.flavi.model.data.database.map.toMoviesDbModel
import com.example.flavi.model.data.datasource.MovieService
import com.example.flavi.model.domain.entity.FilterMovies
import com.example.flavi.model.domain.entity.MovieCard
import com.example.flavi.model.domain.entity.Movies
import com.example.flavi.model.domain.entity.User
import com.example.flavi.model.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val movieService: MovieService,
    private val getFirebaseAuth: GetFirebaseAuth
) : UserRepository {

    override suspend fun userRegister(name: String, password: String, email: String) {
        val userId = getFirebaseAuth.getIdUser()
        val userDbModel = UserDbModel(
            userId = userId,
            name = name,
            email = email,
            password = password
        )
        userDao.insertUserToDb(
            userDbModel = userDbModel,
            movies = MovieCard().toMoviesDbModel(userId),
            userId
        )
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) {
        userDao.removeMovieFromDatabase(movieId)
    }

    fun getFavoritesMovie(): Flow<List<MoviesDbModel>> {
        val userId = getFirebaseAuth.getIdUser()
        return userDao.getFavoriteMovie(userId)
    }

    suspend fun checkMovieInDbByMovieId(movieId: Int): Boolean {
        val userId = getFirebaseAuth.getIdUser()
        return userDao.checkMovieByTitle(movieId, userId)
    }

    suspend fun saveMovieToDb(movieCard: MovieCard) {
        val id = getFirebaseAuth.getIdUser()
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

    override suspend fun getMovieByTitle(keyword: String): Response<Movies> {
        return movieService.getListMoviesByQuery(keyword)
    }

    suspend fun getFilterMovies(query: String, type: String): Response<FilterMovies> {
        return movieService.getFilterListMovies(query, type)
    }

}