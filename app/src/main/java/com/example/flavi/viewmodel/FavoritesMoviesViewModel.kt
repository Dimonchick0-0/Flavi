package com.example.flavi.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.model.data.repository.UserRepositoryImpl
import com.example.flavi.view.state.FavoritesMoviesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.filterList
import javax.inject.Inject

@HiltViewModel
class FavoritesMoviesViewModel @Inject constructor(
    private val repositoryImpl: UserRepositoryImpl
) : ViewModel() {

    private val _stateFavoritesMovies:
            MutableStateFlow<FavoritesMoviesState> = MutableStateFlow(
        FavoritesMoviesState.InitialState
    )
    val stateFavoritesMovies = _stateFavoritesMovies.asStateFlow()

    fun processInitialState() {
        viewModelScope.launch(Dispatchers.IO) {}
    }

    fun processLoadFavoritesMovies() {
        _stateFavoritesMovies.update { state ->
            if (state is FavoritesMoviesState.LoadFavoritesMovies) {
                Log.d("Auth", state.movieCard.toString())
                state.copy(movieCard = state.movieCard)
            } else {
                state
            }
        }
    }
}