package com.example.flavi.view.screens.components

import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckFavoriteMovieList<T> @Inject constructor() {

    val list = mutableListOf<T>()

    val checkListMovie = mutableStateOf(false)

    fun getStatusListIfIsEmptyOrNotEmpty(): Boolean {
        return list.isEmpty()
    }
}
