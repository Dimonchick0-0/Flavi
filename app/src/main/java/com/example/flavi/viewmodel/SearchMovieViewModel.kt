package com.example.flavi.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.model.data.datasource.CountriesDTO
import com.example.flavi.model.data.datasource.GenresDTO
import com.example.flavi.model.data.datasource.Network
import com.example.flavi.model.data.datasource.PosterDTO
import com.example.flavi.model.data.datasource.RatingDTO
import com.example.flavi.model.domain.entity.MovieCard
import com.example.flavi.model.domain.entity.Movies
import com.example.flavi.model.domain.usecase.GetMovieByTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import kotlin.time.measureTime

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val getMovieByTitleUseCase: GetMovieByTitleUseCase,
    @ApplicationContext context: Context
) : ViewModel() {

    val query = MutableSharedFlow<String>()

    private val _stateSearchMovie: MutableStateFlow<SearchMovieState> =
        MutableStateFlow(SearchMovieState.Initial)
    val stateSearchMovie: StateFlow<SearchMovieState> = _stateSearchMovie.asStateFlow()

    val stateError = mutableStateOf(false)

    val oldQuery = mutableStateOf("")

    val currentQuery = mutableStateOf("")

    val networkState = mutableStateOf(false)

    init {
        changeStateByConnection(context)
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
                                name = movie.name,
                                alternativeName = movie.alternativeName,
                                year = movie.year,
                                posterDTO = movie.poster,
                                ratingDTO = movie.rating,
                                genresDTO = movie.genres.toGenresDTO(),
                                countriesDTO = movie.countries.toCountrie()
                            )
                        )
                    }
                }
            }
            .launchIn(viewModelScope)

    }

    private fun List<GenresDTO>.toGenresDTO(): GenresDTO {
        return this.elementAt(0)
    }

    private fun List<CountriesDTO>.toCountrie(): CountriesDTO {
        return this.elementAt(0)
    }

    private fun List<MovieCard>.isNotFoundMovies(): Boolean {
        return this.isEmpty()
    }

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
                    name = state.name,
                    alternativeName = state.alternativeName,
                    year = state.year,
                    posterDTO = state.posterDTO,
                    ratingDTO = state.ratingDTO,
                    genresDTO = state.genresDTO,
                    countriesDTO = state.countriesDTO
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
                val name = state.name
                val alternativeName = state.alternativeName
                val year = state.year
                val poster = state.posterDTO
                val rating = state.ratingDTO
                val genre = state.genresDTO
                val countrie = state.countriesDTO
                state.copy(
                    id = id,
                    name = name,
                    alternativeName = alternativeName,
                    year = year,
                    posterDTO = poster,
                    ratingDTO = rating,
                    genresDTO = genre,
                    countriesDTO = countrie
                )
            } else {
                state
            }
        }
    }

    fun processNetworkShutdownState() {
        _stateSearchMovie.update { state ->
            if (state is SearchMovieState.NetworkShutdown) {
                viewModelScope.launch {
                    _stateSearchMovie.emit(SearchMovieState.NotFound)
                }
                state
            } else {
                state
            }
        }
    }

    fun processConnectToTheNetwork() {
        _stateSearchMovie.update { state ->
            if (state is SearchMovieState.ConnectToTheNetwork) {
                viewModelScope.launch {
                    _stateSearchMovie.emit(SearchMovieState.Initial)
                }
                state
            } else {
                state
            }
        }
    }

    private fun changeStateByConnection(context: Context) {
        networkState.value = Network(context).stateNetwork()
        val network = Network(context)
        if (!networkState.value) {
            viewModelScope.launch {
                _stateSearchMovie.emit(SearchMovieState.NotFound)
            }
        }
        network.lostNetwork {
            networkState.value = false
            viewModelScope.launch {
                _stateSearchMovie.emit(SearchMovieState.NetworkShutdown)
            }
        }
        network.onAvailableNetwork {
            networkState.value = true
            viewModelScope.launch {
                _stateSearchMovie.emit(SearchMovieState.ConnectToTheNetwork)
            }
        }
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

    data object NotFound : SearchMovieState

    data object NetworkShutdown : SearchMovieState

    data object ConnectToTheNetwork : SearchMovieState

    data class InputQuery(
        val query: String
    ) : SearchMovieState

    data class LoadMovie(
        val id: Int,
        val name: String,
        val alternativeName: String,
        val year: Int,
        val posterDTO: PosterDTO,
        val ratingDTO: RatingDTO,
        val genresDTO: GenresDTO,
        val countriesDTO: CountriesDTO
    ) : SearchMovieState
}