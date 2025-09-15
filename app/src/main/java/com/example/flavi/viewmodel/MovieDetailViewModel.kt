package com.example.flavi.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.model.data.database.map.toMovieCard
import com.example.flavi.model.data.database.map.toMovieCardEntity
import com.example.flavi.model.data.repository.UserRepositoryImpl
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieCard
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail
import com.example.flavi.view.screens.components.CheckFavoriteMovieList
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
        MutableStateFlow(MovieDetailState.LoadMovieDetail(MovieDetail()))

    val movieDetailState: StateFlow<MovieDetailState> = _movieDetailState.asStateFlow()

    val checkMovieInFavorite = mutableStateOf(false)

    fun mapMovieDetailToMovieCard(movie: MovieDetail): MovieCard {
        return movie.toMovieCard()
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
                state.copy(movie = state.movie)
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
                _movieDetailState.emit(MovieDetailState.LoadMovieDetail(it))
            }
        }
    }

}

sealed interface MovieDetailState {
    data class LoadMovieDetail(val movie: MovieDetail) : MovieDetailState
}