package com.example.flavi.view.screens.components

import com.example.flavi.model.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckIsItClosedApp @Inject constructor(
    private val movieRepository: MovieRepository
) {

    private val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    private val coroutineScope = CoroutineScope(dispatcher)

    fun ifAppClosedThenDeleteMovieDetailFromDatabase() {
        coroutineScope.launch(Dispatchers.IO) {
            movieRepository.removeMovieDetail()
        }
    }
}