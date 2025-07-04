package com.example.flavi.model.data.repository

import com.example.flavi.model.data.database.dao.UserDao
import com.example.flavi.model.data.database.entitydb.UserDbModel
import com.example.flavi.model.data.database.map.toEntity
import com.example.flavi.model.data.datasource.MovieService
import com.example.flavi.model.domain.entity.Movies
import com.example.flavi.model.domain.entity.User
import com.example.flavi.model.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val movieService: MovieService
    ): UserRepository {

    override suspend fun userRegister(name: String, password: String, email: String) {
        val userDbModel = UserDbModel(
            id = 0,
            name = name,
            email = email,
            password = password
        )
        userDao.insertUserToDB(userDbModel)
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