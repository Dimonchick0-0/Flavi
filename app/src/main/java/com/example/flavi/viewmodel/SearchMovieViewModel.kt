package com.example.flavi.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.model.data.datasource.PosterDTO
import com.example.flavi.model.domain.entity.Movie
import com.example.flavi.model.domain.entity.Movies
import com.example.flavi.model.domain.usecase.GetMovieByTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val getMovieByTitleUseCase: GetMovieByTitleUseCase
) : ViewModel() {

    val query = MutableSharedFlow<String>()

    private val _stateSearchMovie: MutableStateFlow<SearchMovieState> =
        MutableStateFlow(SearchMovieState.Initial)
    val stateSearchMovie: StateFlow<SearchMovieState> = _stateSearchMovie.asStateFlow()

    val stateError = mutableStateOf(false)

    val oldQuery = mutableStateOf("")

    val currentQuery = mutableStateOf("")

    init {
        query
            .onEach {
                _stateSearchMovie.emit(SearchMovieState.InputQuery(it))
                oldQuery.value = it
            }
            .map {
                getMovie(it)
            }
            .onEach {
                if (it.body()?.docs?.isNotFoundMovies()!!) {
                    _stateSearchMovie.emit(SearchMovieState.NotFound)
                }
            }
            .onEach {
                it.body()?.let { movies ->
                    movies.docs.forEach { movie ->
                        _stateSearchMovie.emit(
                            SearchMovieState.LoadMovie(
                                id = movie.id,
                                logo = movie.poster,
                                name = movie.name,
                                year = movie.year,
                                description = movie.description
                            )
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun List<Movie>.isNotFoundMovies(): Boolean { return this.isEmpty() }

    fun processNotFoundMovie(query: String) {
        _stateSearchMovie.update { state ->
            if (state is SearchMovieState.NotFound) {
                oldQuery.value = query
                currentQuery.value = oldQuery.value
                state
            } else {
                state
            }
        }
    }

    fun updateQuery(newQuery: String) {
        _stateSearchMovie.update { state ->
            if (state is SearchMovieState.LoadMovie) {
                oldQuery.value = newQuery
                currentQuery.value = oldQuery.value
                state.copy(
                    id = state.id,
                    logo = state.logo,
                    name = state.name,
                    year = state.year,
                    description = state.description
                )
            } else {
                state
            }
        }
    }

    fun processLoadMovie() {
        _stateSearchMovie.update { state ->
            if (state is SearchMovieState.LoadMovie) {
                val id = state.id
                val logo = state.logo
                val name = state.name
                val year = state.year
                val description = state.description
                state.copy(id, logo, name, year, description)
            } else {
                state
            }
        }
    }

    private fun isOnline(): Boolean {
        TODO()
    }

    private suspend fun getMovie(query: String): Response<Movies> {
        return withContext(Dispatchers.Default) {
            getMovieByTitleUseCase(
                page = 1,
                limit = 1,
                query = query
            )
        }
    }

    fun processQuery(query: String) {
        _stateSearchMovie.update { state ->
            if (state is SearchMovieState.InputQuery) {
                state.copy(query = query)
            } else {
                state
            }
        }
    }

    fun processInitial() {
        viewModelScope.launch { _stateSearchMovie.emit(SearchMovieState.InputQuery("")) }
    }
}

sealed interface SearchMovieState {
    data object Initial : SearchMovieState

    data object NotFound: SearchMovieState

    data class InputQuery(
        val query: String
    ) : SearchMovieState

    data class LoadMovie(
        val id: Int,
        val logo: PosterDTO,
        val name: String,
        val year: Int,
        val description: String
    ): SearchMovieState
}