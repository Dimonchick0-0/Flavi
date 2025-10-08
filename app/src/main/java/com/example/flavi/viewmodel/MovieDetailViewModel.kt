package com.example.flavi.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.model.data.database.map.toActor
import com.example.flavi.model.data.database.map.toMovieCard
import com.example.flavi.model.data.database.map.toMovieCardEntity
import com.example.flavi.model.data.database.map.toPosterEntity
import com.example.flavi.model.data.repository.UserRepositoryImpl
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Actor
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieCard
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Poster
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
    private val repositoryImpl: UserRepositoryImpl
) : ViewModel() {

    private val _movieDetailState: MutableStateFlow<MovieDetailState> =
        MutableStateFlow(MovieDetailState.LoadMovieDetail(MovieDetail(), listOf()))

    val movieDetailState: StateFlow<MovieDetailState> = _movieDetailState.asStateFlow()

    private val listActors = mutableListOf<Actor>()

    val checkMovieInFavorite = mutableStateOf(false)

    fun mapMovieDetailToMovieCard(movie: MovieDetail): MovieCard = movie.toMovieCard()

    fun getActors(filmId: Int) {
        viewModelScope.launch {
            repositoryImpl.getActorsFromMovie(filmId)
                .body()?.forEach { actorDto ->
                    listActors.add(actorDto.toActor())
                }
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

    suspend fun processLoadMovieDetailState() {
        _movieDetailState.update { state ->
            if (state is MovieDetailState.LoadMovieDetail) {
                val movie = CheckFavoriteMovieList<MovieCard>()
                if (checkMovieInFavorite(state.movie.kinopoiskId)) {
                    movie.checkListMovie.value = true
                }
                state.copy(movie = state.movie, listActors)
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
        repositoryImpl.saveMovieToDb(movie.toMovieCardEntity())
    }

    fun getMovieById(filmId: Int) {
        viewModelScope.launch {
            repositoryImpl.getMovieDetailById(filmId).body()?.let {
                _movieDetailState.emit(MovieDetailState.LoadMovieDetail(it, listActors))
            }
        }
    }

}

