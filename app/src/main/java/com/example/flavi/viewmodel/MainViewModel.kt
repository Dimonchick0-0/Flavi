package com.example.flavi.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.model.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {

    init {
        Log.d("Auth", "Появилась вью модель")
    }

    private var checkIfAllDeleteFromDatabase by mutableStateOf(false)

    fun ifAppClosedThenDeleteMovieDetailFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.removeMovieDetail()
            checkIfAllDeleteFromDatabase = true
            Log.d("Auth", checkIfAllDeleteFromDatabase.toString())
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("Auth", "Вью модель очистилась")
    }

}