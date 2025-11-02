package com.example.flavi.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.model.data.database.map.toPerson
import com.example.flavi.model.data.repository.UserRepositoryImpl
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Person
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.ProfessionKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonsScreenViewModel @Inject constructor(
    private val repositoryImpl: UserRepositoryImpl
) : ViewModel() {

    private val _state: MutableStateFlow<PersonScreenState> = MutableStateFlow(
        value = PersonScreenState(Person())
    )
    val state = _state.asStateFlow()

    val stateProfessionKeuPerson = mutableStateOf(ProfessionKey.NOT_SELECTED)

    fun getPersonDetail(id: Int) {
        viewModelScope.launch {
            try {
                repositoryImpl.getPersonDetail(id).body()?.let {
                    _state.emit(value = PersonScreenState(person = it.toPerson()))
                }
            } catch (e: Exception) {
                Log.e("Auth", "Ошибка: ${e.message}")
            }
        }
    }

    fun processPersonScreenState() {
        _state.update { state ->
            state.copy(person = state.person)
        }
    }

}

data class PersonScreenState(val person: Person)