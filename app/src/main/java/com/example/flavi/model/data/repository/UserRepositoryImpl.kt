package com.example.flavi.model.data.repository

import com.example.flavi.model.data.database.dao.UserDao
import com.example.flavi.model.data.database.entitydb.HistorySearchDb
import com.example.flavi.model.data.database.entitydb.UserDbModel
import com.example.flavi.model.data.database.map.toEntity
import com.example.flavi.model.data.database.map.toListPosterEntity
import com.example.flavi.model.data.database.map.toMoviesDbModel
import com.example.flavi.model.data.datasource.actors.ActorDTO
import com.example.flavi.model.data.datasource.actors.ListActor
import com.example.flavi.model.data.datasource.images.ImageMovieDTO
import com.example.flavi.model.data.datasource.rental.Rental
import com.example.flavi.model.data.datasource.services.KinoposikService
import com.example.flavi.model.data.datasource.services.MovieService
import com.example.flavi.model.domain.entity.HistorySearch
import com.example.flavi.model.domain.entity.User
import com.example.flavi.model.domain.entity.kinopoiskDev.MoviesKinopoiskDev
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieCard
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Movies
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Poster
import com.example.flavi.model.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val movieService: MovieService,
    private val kinoposikService: KinoposikService,
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
        userDao.insertUserToDB(userDbModel)
    }

    suspend fun getAwardsByMovieId(id: Int) = movieService.getAwardsMovieByMovieId(id)

    suspend fun getActorsFromMovie(filmId: Int): Response<List<ActorDTO>> {
        return movieService.getActorByMovieId(filmId)
    }

    suspend fun getRentalMovies(id: Int): Response<Rental> {
        return movieService.getRentalMovie(id)
    }

    suspend fun getSearchActors(nameActor: String): Response<ListActor> {
        return movieService.getSearchActor(nameActor)
    }

    suspend fun loadAllImageMovie(id: Int, type: String): List<Poster> {
        return loadImageMovieById(id, type).body()?.items?.toListPosterEntity()!!
    }

    suspend fun loadImageMovieById(id: Int, type: String): Response<ImageMovieDTO> {
        return movieService.loadImageFilmById(id, type)
    }

    override suspend fun removeHistorySearch(title: String) {
        userDao.removeHistorySearchBiId(title)
    }

    suspend fun insertTitleMovieInToDatabase(title: String) {
        val userId = getFirebaseAuth.getIdUser()
        userDao.insertTitleMovieInToDatabase(title = HistorySearchDb(id = 0, title = title, userId = userId))
    }

    suspend fun getMovieDetailById(id: Int): Response<MovieDetail> {
        return movieService.getMovieById(id)
    }

    override suspend fun removeMovieFromFavorite(movieId: Int) {
        userDao.removeMovieFromDatabase(movieId)
    }

    fun getHistorySearchList(): Flow<List<HistorySearch>> {
        val userId = getFirebaseAuth.getIdUser()
        return userDao.getHistorySearch(userId).map { it.toEntity() }
    }

    fun getFavoritesMovie(): Flow<List<MovieCard>> {
        val userId = getFirebaseAuth.getIdUser()
        return userDao.getFavoriteMovie(userId)
    }

    suspend fun checkMovieInDbByMovieId(movieId: Int): Boolean {
        val userId = getFirebaseAuth.getIdUser()
        return userDao.checkMovieByTitle(movieId, userId)
    }

    suspend fun saveMovieToDb(movieCard: MovieCard) {
        val userId = getFirebaseAuth.getIdUser()
        withContext(Dispatchers.IO) {
            userDao.insertMovieToDb(movieCard.toMoviesDbModel(userId = userId))
        }
    }

    override suspend fun authUser(password: String, email: String): User {
        return userDao.getUserByPasswordAndEmail(password, email).toEntity()
    }

    suspend fun checkUser(email: String, password: String): Boolean {
        return userDao.checkUserByEmailAndPassword(email, password)
    }

    override suspend fun getMovieByTitle(keyword: String, page: Int): Response<Movies> {
        return movieService.getListMoviesByQuery(keyword, page)
    }

    suspend fun getMovieFilter(query: String): Response<MoviesKinopoiskDev> {
        return kinoposikService.getListMovie(query)
    }
}