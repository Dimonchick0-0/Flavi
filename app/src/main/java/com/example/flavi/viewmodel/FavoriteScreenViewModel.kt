package com.example.flavi.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.model.data.repository.UserRepositoryImpl
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieCard
import com.example.flavi.model.domain.usecase.RemovieMovieFromFavoritesUseCase
import com.example.flavi.view.screens.components.CheckFavoriteMovieList
import com.example.flavi.view.state.FavoriteScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val repositoryImpl: UserRepositoryImpl,
    private val removeMovieFromFavoritesUseCase: RemovieMovieFromFavoritesUseCase
): ViewModel() {

    private val _favoriteState: MutableStateFlow<FavoriteScreenState> = MutableStateFlow(
        FavoriteScreenState.LoadMovies(emptyList())
    )
    val favoriteState: StateFlow<FavoriteScreenState> = _favoriteState.asStateFlow()

    val checkMovieInFavorite = mutableStateOf(false)

    suspend fun processFavoriteMovieList() {
        _favoriteState.update { state ->
            if (state is FavoriteScreenState.LoadMovies) {
                if (state.movieList.isNotEmpty()) {
                    state.copy(movieList = state.movieList)
                } else {
                    _favoriteState.emit(FavoriteScreenState.EmptyList(emptyList()))
                    state
                }
            } else {
                state
            }
        }
    }

    suspend fun checkingForAnEmptyListAndIfIsEmptyToEmittedEmptyList() {
        val checkMovie = CheckFavoriteMovieList<MovieCard>()
        if (checkMovie.getStatusListIfIsEmptyOrNotEmpty()) {
            _favoriteState.emit(FavoriteScreenState.EmptyList(checkMovie.list))
        }

    }

    suspend fun processEmptyMovieList() {
        _favoriteState.update { state ->
            if (state is FavoriteScreenState.EmptyList) {
                if (state.movieList.isEmpty()) {
                    state.copy(movieList = state.movieList)
                } else {
                    _favoriteState.emit(FavoriteScreenState.LoadMovies(state.movieList))
                    state
                }
            } else {
                state
            }
        }
    }

    suspend fun removeMovieInFavoriteById(movieId: Int) {
        removeMovieFromFavoritesUseCase(movieId)
    }

    fun getMovieCard() {
        viewModelScope.launch {
            repositoryImpl.getFavoritesMovie().collect {
                _favoriteState.emit(FavoriteScreenState.LoadMovies(movieList = it))
            }
        }
    }

    fun saveMovieToFavorite(movieCard: MovieCard) {
        viewModelScope.launch {
            repositoryImpl.saveMovieToDb(movieCard)
        }
    }

    suspend fun checkingMovieInFavorite(movieId: Int): Boolean {
        return repositoryImpl.checkMovieInDbByMovieId(movieId)
    }
}

