package com.example.flavi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.model.data.database.map.toListEntityAwards
import com.example.flavi.model.data.repository.UserRepositoryImpl
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Awards
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.internal.filterList
import javax.inject.Inject

@HiltViewModel
class AwardsScreenViewModel @Inject constructor(
    private val repositoryImpl: UserRepositoryImpl
) : ViewModel() {

    private val _state: MutableStateFlow<AwardsScreenState> = MutableStateFlow(
        AwardsScreenState.AwardsScreen(awards = listOf())
    )
    val state = _state.asStateFlow()

    fun getAwards(filmId: Int) {
        viewModelScope.launch {
            repositoryImpl.getAwardsByMovieId(filmId).body()?.items
                ?.filterList { persons.isNotEmpty() }
                ?.let {
                _state.emit(AwardsScreenState.AwardsScreen(awards = it.toListEntityAwards()))
            }
        }
    }

}

sealed interface AwardsScreenState {
    data class AwardsScreen(val awards: List<Awards>) : AwardsScreenState
}