package com.example.flavi.view.screens.components

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CheckFavoriteMovieList<T> @Inject constructor() {

    val list = mutableListOf<T>()

}