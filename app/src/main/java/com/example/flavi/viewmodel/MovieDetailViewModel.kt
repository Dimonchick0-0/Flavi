package com.example.flavi.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.model.data.database.map.toActor
import com.example.flavi.model.data.database.map.toMovieCard
import com.example.flavi.model.data.database.map.toMovieCardEntity
import com.example.flavi.model.data.database.map.toPosterEntity
import com.example.flavi.model.data.database.map.toReview
import com.example.flavi.model.data.database.map.toSequelsAndPrequels
import com.example.flavi.model.data.database.map.toSimilar
import com.example.flavi.model.data.datasource.rental.RentalMovie
import com.example.flavi.model.data.repository.GetFirebaseAuth
import com.example.flavi.model.data.repository.MovieRepository
import com.example.flavi.model.data.repository.UserRepositoryImpl
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Actor
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieCard
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MoviesSequelAndPrequel
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Poster
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Review
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.SimilarMovie
import com.example.flavi.view.screens.components.CheckFavoriteMovieList
import com.example.flavi.view.state.MovieDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repositoryImpl: UserRepositoryImpl,
    private val movieRepository: MovieRepository,
    private val getFirebaseAuth: GetFirebaseAuth
) : ViewModel() {

    private val _movieDetailState: MutableStateFlow<MovieDetailState> =
        MutableStateFlow(
            MovieDetailState.LoadMovieDetail(
                MovieDetail(),
                listOf(),
                setOf(),
                listOf(),
                listOf(),
                listOf()
            )
        )

    val movieDetailState: StateFlow<MovieDetailState> = _movieDetailState.asStateFlow()

    private val listActors = mutableListOf<Actor>()

    val checkMovieInFavorite = mutableStateOf(false)

    private val rentalList = mutableSetOf<RentalMovie>()

    private val reviewList = mutableListOf<Review>()

    private val similarList = mutableListOf<SimilarMovie>()

    private val sequelsAndPrequelsList = mutableListOf<MoviesSequelAndPrequel>()

    val checkRegisteredUser = mutableStateOf(false)

    suspend fun checkIfThereIsMovieInDb(movieId: Int): Boolean {
        return movieRepository.checkIfThereIsMovieInDb(movieId = movieId)
    }

    fun mapMovieDetailToMovieCard(movie: MovieDetail): MovieCard = movie.toMovieCard()

    suspend fun insertMovieDetailToDb(movie: MovieDetail, movieId: Int) {
        if (listActors.isNotEmpty()
            && rentalList.isNotEmpty()
            && reviewList.isNotEmpty()
            && similarList.isNotEmpty()
            && sequelsAndPrequelsList.isNotEmpty()) {
            movieRepository.insertMovieDetailToDb(
                movieId = movieId,
                actors = listActors,
                rentals = rentalList,
                reviews = reviewList,
                similars = similarList,
                sequalsAndPrequals = sequelsAndPrequelsList,
                movieDetail = movie
            )
        }

    }

    fun getSequelsAndPrequelsMovie(filmId: Int) {
        viewModelScope.launch {
            repositoryImpl.getSequelsAndPrequelsMovie(filmId).body()?.forEach {
                sequelsAndPrequelsList.add(it.toSequelsAndPrequels())
            }
        }
    }

    fun getSimilars(filmId: Int) {
        viewModelScope.launch {
            repositoryImpl.getSimilarsMovie(filmId).body()?.items
                ?.distinctBy { it.filmId }
                ?.filter { it.nameEn != null }
                ?.forEach {
                    similarList.add(it.toSimilar())
                }
        }
    }

    fun getActors(filmId: Int) {
        viewModelScope.launch {
            repositoryImpl.getActorsFromMovie(filmId).body()?.forEach { actorDto ->
                listActors.add(actorDto.toActor())
            }
        }
    }

    fun getReviews(id: Int) {
        viewModelScope.launch {
            repositoryImpl.getReviewsListByMovieId(id).body()?.items?.forEach {
                reviewList.add(it.toReview())
            }
        }
    }

    fun getRental(id: Int) {
        viewModelScope.launch {
            repositoryImpl.getRentalMovies(id).body()?.items
                ?.distinctBy { it.country }
                ?.sortedBy { it.country?.country }
                ?.filter { it.country?.country != null }
                ?.toHashSet()
                ?.forEach { rentalList.add(it) }
        }
    }

    suspend fun loadImageMovie(id: Int, type: String): List<Poster> {
        val listPoster = mutableListOf<Poster>()
        repositoryImpl.loadImageMovieById(id, type).body()?.let { imageMovie ->
            imageMovie.items.forEach {
                listPoster.add(it.toPosterEntity())
            }
        }
        return listPoster.toList()
    }

    fun setStatusMovieFavoriteDuringRemove(movie: MovieDetail) {
        val checkMovieInFavorite = CheckFavoriteMovieList<MovieCard>()
        checkMovieInFavorite.apply {
            list.remove(mapMovieDetailToMovieCard(movie))
            checkListMovie.value = false
        }
    }

    private suspend fun getMovie(movieId: Int) = movieRepository.getMovieDetail(movieId)

    suspend fun loadMovieFromDatabase(movieId: Int) {
        val movie = movieRepository.getMovieDetail(movieId)
        _movieDetailState.emit(
            value = MovieDetailState.LoadMovieDetail(
                movie = movie.movieDetail,
                actors = movie.actors,
                rental = movie.rentals,
                review = movie.reviews,
                similars = movie.similars,
                sequelsAndPreques = movie.sequalsAndPrequals
            )
        )
    }

    suspend fun processLoadMovieDetailState() {
        _movieDetailState.update { state ->
            if (state is MovieDetailState.LoadMovieDetail) {
                val movie = CheckFavoriteMovieList<MovieCard>()
                if (checkMovieInFavorite(state.movie.kinopoiskId)) {
                    movie.checkListMovie.value = true
                }
                if (!checkIfThereIsMovieInDb(state.movie.kinopoiskId)) {
                    state.copy(
                        movie = state.movie,
                        actors = listActors,
                        rental = rentalList,
                        review = reviewList,
                        similars = similarList,
                        sequelsAndPreques = sequelsAndPrequelsList
                    )
                } else {
                    val movieId = state.movie.kinopoiskId
                    state.copy(
                        movie = state.movie,
                        actors = getMovie(movieId).actors,
                        rental = getMovie(movieId).rentals,
                        review = getMovie(movieId).reviews,
                        similars = getMovie(movieId).similars,
                        sequelsAndPreques = getMovie(movieId).sequalsAndPrequals
                    )
                }
            } else {
                state
            }
        }
    }

    suspend fun checkMovieInFavorite(movieId: Int): Boolean {
        return repositoryImpl.checkMovieInDbByMovieId(movieId)
    }

    suspend fun removeMovieFromFavorite(movieId: Int) {
        repositoryImpl.removeMovieFromFavorite(movieId)

    }

    suspend fun saveMovieToFavorite(movie: MovieDetail) {
        if (getFirebaseAuth.getCurrentUser() == null) {
            checkRegisteredUser.value = true
        } else {
            repositoryImpl.saveMovieToDb(movie.toMovieCardEntity())
        }
    }

    fun getMovieById(filmId: Int) {
        viewModelScope.launch {
            repositoryImpl.getMovieDetailById(filmId).body()?.let {
                _movieDetailState.emit(
                    MovieDetailState.LoadMovieDetail(
                        it,
                        listOf(),
                        setOf(),
                        listOf(),
                        listOf(),
                        listOf()
                    )
                )
            }
        }
    }

}
