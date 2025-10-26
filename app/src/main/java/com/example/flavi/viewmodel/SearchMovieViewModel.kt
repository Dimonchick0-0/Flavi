package com.example.flavi.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.model.data.database.map.toActorEntity
import com.example.flavi.model.data.database.map.toFilterMovie
import com.example.flavi.model.data.database.map.toFilterMovieList
import com.example.flavi.model.data.database.map.toMovieCardEntity
import com.example.flavi.model.data.datasource.actors.ListActor
import com.example.flavi.model.data.datasource.network.Network
import com.example.flavi.model.data.repository.UserRepositoryImpl
import com.example.flavi.model.domain.entity.HistorySearch
import com.example.flavi.model.domain.entity.kinopoiskDev.MovieCardKinopoisk
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.FilterMovie
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieCard
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Movies
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.OrderFilterMovie
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.SearchActor
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.TypeFilterMovie
import com.example.flavi.model.domain.usecase.RemovieMovieFromFavoritesUseCase
import com.example.flavi.view.state.SearchMovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.filterList
import retrofit2.Response
import javax.inject.Inject
import kotlin.time.measureTime

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val removeMovieFromFavoritesUseCase: RemovieMovieFromFavoritesUseCase,
    private val repositoryImpl: UserRepositoryImpl,
    @ApplicationContext context: Context
) : ViewModel() {

    val query = MutableSharedFlow<String>()

    private val _stateSearchMovie: MutableStateFlow<SearchMovieState> =
        MutableStateFlow(SearchMovieState.Initial)
    val stateSearchMovie: StateFlow<SearchMovieState> = _stateSearchMovie.asStateFlow()

    val showDialog = mutableStateOf(false)

    private val oldQuery = mutableStateOf("")

    val networkState = mutableStateOf(false)

    val notificationOfInternetLoss = mutableStateOf("У вас проблемы с подключением сети")

    val searchMovieInTheDB = mutableStateOf(false)

    val listHistorySearch = mutableStateOf(listOf<String>())

    private val listActor = mutableStateOf(SearchActor())

    val checkActor = mutableStateOf(false)

    val genreMovie = mutableStateOf("")

    var order by mutableStateOf(OrderFilterMovie.NOT_SELECTED)

    var typeMovie by mutableStateOf(TypeFilterMovie.NOT_SELECTED)

    val checkLoadFilterMovie = mutableStateOf(false)

    val checkResetFilters = mutableStateOf(false)

    init {
        changeStateByConnection(context)
        query
            .onEach {
                _stateSearchMovie.emit(SearchMovieState.InputQuery(it))
                oldQuery.value = it
            }
            .retry(retries = 3L)
            .launchIn(viewModelScope)
    }

    private fun updateStateFilterMovie(filterMovie: List<FilterMovie>) {
        _stateSearchMovie.update { state ->
            if (state is SearchMovieState.LoadListMovieWithFilters) {
                state.copy(listMovie = filterMovie)
            } else {
                state
            }
        }
    }

    fun resetFilters() {
        order = OrderFilterMovie.NOT_SELECTED
        typeMovie = TypeFilterMovie.NOT_SELECTED
        genreMovie.value = ""
        checkLoadFilterMovie.value = false
        updateStateFilterMovie(filterMovie = emptyList())
        checkResetFilters.value = true
    }

    fun getMovieByFilter(
        order: String,
        type: String,
        keyword: String
    ): List<FilterMovie> {
        val filterListMovie = mutableListOf<FilterMovie>()

        viewModelScope.launch {
            repositoryImpl.getMovieByFilter(
                order = order,
                type = type,
                keyword = keyword
            ).body()?.items?.forEach {
                filterListMovie.add(it.toFilterMovie())
                checkLoadFilterMovie.value = true
            }
        }

        return filterListMovie
    }

    suspend fun setStateLoadFilterMovie(filterMovie: List<FilterMovie>) {
        _stateSearchMovie.emit(
            value = SearchMovieState.LoadListMovieWithFilters(
                listMovie = filterMovie
            )
        )
    }

    fun getMovieByFiltersOnlyByName(query: String): List<FilterMovie> {
        val filterListMovie = mutableListOf<FilterMovie>()

        viewModelScope.launch {
            repositoryImpl.getMovieFilter(query = query).body()?.docs?.forEach {
                filterListMovie.add(it.toFilterMovieList())
                checkLoadFilterMovie.value = true
            }
        }

        return filterListMovie
    }

    suspend fun gettingTheEnteredQuery() {
        getActors(oldQuery.value).body()?.let { listActors ->
            if (listActors.items.isNotEmpty()) {
                listActors.items.forEach {
                    listActor.value = it.toActorEntity()
                    checkActor.value = true
                }
            } else {
                checkActor.value = false
            }
        }
        getMovie(oldQuery.value).body()?.also { movies ->
            val filteredList = movies.films.filterList {
                if (nameEn == listActor.value.nameEn) {
                    checkActor.value = false
                }
                rating != "null"
            }

            if (filteredList.isNotFoundMovies()) {
                _stateSearchMovie.emit(SearchMovieState.NotFound)
            }

            _stateSearchMovie.emit(
                SearchMovieState.LoadMovieAndActors(
                    movie = filteredList,
                    searchActor = listActor.value
                )
            )
        }
    }

    private suspend fun getActors(nameActor: String): Response<ListActor> {
        return withContext(Dispatchers.Default) {
            repositoryImpl.getSearchActors(nameActor)
        }
    }

    suspend fun removeHistorySearch(title: String) {
        repositoryImpl.removeHistorySearch(title = title)
    }

    suspend fun showHistorySearch() {
        getSearchHistoryList().collect {
            listHistorySearch.value = it.map { historySearch ->
                historySearch.title
            }.distinct()
            if (listHistorySearch.value.isEmpty())
                processInitial()
        }
    }

    private fun getSearchHistoryList(): Flow<List<HistorySearch>> {
        return repositoryImpl.getHistorySearchList()
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

    suspend fun saveHistorySearch(title: String) {
        repositoryImpl.insertTitleMovieInToDatabase(title)
    }

    fun mapFilterMovieCardToMovieCardEntity(filterMovie: FilterMovie): MovieCard {
        return filterMovie.toMovieCardEntity()
    }

    private fun List<MovieCard>.isNotFoundMovies(): Boolean {
        return isEmpty()
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
            repositoryImpl.getMovieByTitle(keyword, page = 1)
        }
    }

    fun getMovieGenres() {
        viewModelScope.launch { _stateSearchMovie.emit(value = SearchMovieState.GenresMovie) }
    }

    fun processInitial() {
        viewModelScope.launch { _stateSearchMovie.emit(SearchMovieState.InputQuery("")) }
    }
}