package com.example.flavi.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.model.data.database.map.toFilterMovieCard
import com.example.flavi.model.data.datasource.Network
import com.example.flavi.model.data.repository.UserRepositoryImpl
import com.example.flavi.model.domain.entity.FilterMovieCard
import com.example.flavi.model.domain.entity.MovieCard
import com.example.flavi.model.domain.entity.Movies
import com.example.flavi.model.domain.usecase.GetMovieByTitleUseCase
import com.example.flavi.model.domain.usecase.RemovieMovieFromFavoritesUseCase
import com.example.flavi.view.state.SearchMovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
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
import okhttp3.internal.filterList
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val getMovieByTitleUseCase: GetMovieByTitleUseCase,
    private val removeMovieFromFavoritesUseCase: RemovieMovieFromFavoritesUseCase,
    private val repositoryImpl: UserRepositoryImpl,
    @ApplicationContext context: Context
) : ViewModel() {

    val query = MutableSharedFlow<String>()

    val filters = MutableSharedFlow<String>()

    private val _stateSearchMovie: MutableStateFlow<SearchMovieState> =
        MutableStateFlow(SearchMovieState.Initial)
    val stateSearchMovie: StateFlow<SearchMovieState> = _stateSearchMovie.asStateFlow()

    val showDialog = mutableStateOf(false)

    val stateError = mutableStateOf(false)

    val oldQuery = mutableStateOf("")

    val currentQuery = mutableStateOf("")

    val networkState = mutableStateOf(false)

    val notificationOfInternetLoss = mutableStateOf("У вас проблемы с подключением сети")

    val searchMovieInTheDB = mutableStateOf(false)

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
                it.body()?.also { movies ->
                    val newMoviesList = movies.films.filterList {
                        rating != "null"
                    }
                    _stateSearchMovie.emit(SearchMovieState.LoadMovie(newMoviesList))
                }
            }
            .onEach {
                it.body()?.also { movies ->
                    if (movies.films.isNotFoundMovies()) {
                        _stateSearchMovie.emit(SearchMovieState.NotFound)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    suspend fun removeMovieFromFavorites(movieId: Int) {
        removeMovieFromFavoritesUseCase(movieId)
    }

    suspend fun checkMovieByTitle(movieId: Int): Boolean {
        return repositoryImpl.checkMovieInDbByMovieId(movieId)
    }

    fun saveMovieInTheFavorites(movieCard: MovieCard) {
        viewModelScope.launch {
            repositoryImpl.saveMovieToDb(movieCard)
        }
    }

    private fun List<MovieCard>.isNotFoundMovies(): Boolean {
        return isEmpty()
    }

    fun clearQuery() {
        oldQuery.value = ""
        processInitial()
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
                state.copy(movie = state.movie)
            } else {
                state
            }
        }
    }

    fun processLoadMovie() {
        _stateSearchMovie.update { state ->
            if (state is SearchMovieState.LoadMovie) {
                state.copy(movie = state.movie)
            } else {
                state
            }
        }
    }

    fun processNotificationOfInternetLoss() {
        _stateSearchMovie.update { state ->
            if (state is SearchMovieState.NotificationOfInternetLoss) {
                state.copy(notificationOfInternetLoss.value)
            } else {
                state
            }
        }
    }

    fun processNetworkShutdownState() {
        _stateSearchMovie.update { state ->
            if (state is SearchMovieState.NetworkShutdown) {
                viewModelScope.launch {
                    _stateSearchMovie.emit(
                        SearchMovieState.NotificationOfInternetLoss(
                            notification = notificationOfInternetLoss.value
                        )
                    )
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
                _stateSearchMovie.emit(
                    SearchMovieState.NotificationOfInternetLoss(
                        notificationOfInternetLoss.value
                    )
                )
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

    private suspend fun getMovie(keyword: String): Response<Movies> {
        return withContext(Dispatchers.Default) {
            repositoryImpl.getMovieByTitle(keyword)
        }
    }

    fun setFiltersToMovies() {
        filters.onEach {
            _stateSearchMovie.emit(SearchMovieState.SwitchingFiltersState(it))
        }.launchIn(viewModelScope)
    }

    suspend fun processGetFilters(genresName: String) {
        _stateSearchMovie.update { state ->
            if (state is SearchMovieState.SwitchingFiltersState) {
                repositoryImpl.getFilterMovies(genresName, type = "FILM").body()?.let { filterMovies ->
                    val newFilterMovie = filterMovies.items.filterList {
                        ratingImdb != 0.0f
                    }
                    _stateSearchMovie.emit(SearchMovieState.LoadListMovieWithFilters(newFilterMovie))
                }
                state.copy(filter = genresName)
            } else {
                state
            }
        }
    }

    fun processLoadMovieListWithFilters() {
        _stateSearchMovie.update { state ->
            if (state is SearchMovieState.LoadListMovieWithFilters) {
                state.copy(listMovie = state.listMovie)
            } else {
                state
            }
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