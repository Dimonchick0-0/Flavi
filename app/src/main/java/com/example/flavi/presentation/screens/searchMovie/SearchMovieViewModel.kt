package com.example.flavi.presentation.screens.searchMovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.domain.usecase.GetMovieByTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val getMovieByTitleUseCase: GetMovieByTitleUseCase
) : ViewModel() {

    val query = MutableSharedFlow<String>()

    private val _stateSearchMovie: MutableStateFlow<SearchMovieState> =
        MutableStateFlow(SearchMovieState.Initial)
    val stateSearchMovie: StateFlow<SearchMovieState> = _stateSearchMovie.asStateFlow()

    init {
        query
            .onEach {
                _stateSearchMovie.emit(SearchMovieState.InputQuery(it))
                getMovieByTitleUseCase(
                    page = 1,
                    limit = 1,
                    query = it
                )
            }
            .onEach {
                if (it.isEmpty()) {
                    _stateSearchMovie.emit(SearchMovieState.Error)
                }
            }.launchIn(viewModelScope)
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

    data class InputQuery(
        val query: String
    ) : SearchMovieState

    data object Error : SearchMovieState
}