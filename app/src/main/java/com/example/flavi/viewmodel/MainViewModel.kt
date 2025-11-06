package com.example.flavi.viewmodel

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

    fun ifAppClosedThenDeleteMovieDetailFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.removeMovieDetail()
        }
    }

}