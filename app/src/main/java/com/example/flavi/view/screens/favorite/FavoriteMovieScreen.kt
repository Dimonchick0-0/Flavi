package com.example.flavi.view.screens.favorite

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.flavi.model.domain.entity.MovieCard
import com.example.flavi.view.navigation.BottomNavigation
import com.example.flavi.view.screens.searchMovie.MovieCardComponent
import com.example.flavi.viewmodel.FavoriteScreenState
import com.example.flavi.viewmodel.FavoriteScreenViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    onClickToMovieDetailScreen: (MovieCard) -> Unit,
    viewModel: FavoriteScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {

    val state = viewModel.favoriteState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            BottomNavigation.BottomNav(
                navHostController = navHostController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onBackground)
        ) {
            when (val currentState = state.value) {

                is FavoriteScreenState.LoadMovies -> {
                    coroutineScope.launch {
                        viewModel.processFavoriteMovieList()
                    }
                    viewModel.getMovieCard()
                    currentState.movieList.forEach { movie ->
                        val colorRating = if (movie.rating > "5.0") Color.Green else Color.Red
                        MovieCardComponent(
                            onClickSaveMovie = {
                                viewModel.apply {
                                    saveMovieToFavorite(movie)
                                    checkMovieInFavorite.value = true
                                }
                            },
                            onClickCheckingMovie = {
                                coroutineScope.launch {
                                    if (viewModel.checkingMovieInFavorite(movie.filmId)) {
                                        viewModel.checkMovieInFavorite.value = true
                                    }
                                    if (!viewModel.checkingMovieInFavorite(movieId = movie.filmId)) {
                                        viewModel.checkMovieInFavorite.value = false
                                    }
                                }
                            },
                            onClickRemoveMovie = {
                                coroutineScope.launch {
                                    viewModel.apply {
                                        removeMovieInFavoriteById(movie.filmId)
                                        checkMovieInFavorite.value = false
                                        checkingForAnEmptyListAndIfIsEmptyToEmittedEmptyList()
                                    }
                                }
                            },
                            onClickGetMovieDetail = {
                                onClickToMovieDetailScreen(movie)
                            },
                            searchMovie = viewModel.checkMovieInFavorite.value,
                            movieImage = movie.posterUrlPreview,
                            movieNameRu = movie.nameRu,
                            nameOriginal = movie.nameEn,
                            movieYear = movie.year,
                            movieGenre = movie.genres.first().genre,
                            movieRating = movie.rating,
                            movieColorRating = colorRating
                        )
                    }
                }

                is FavoriteScreenState.EmptyList -> {
                    coroutineScope.launch {
                        viewModel.processEmptyMovieList()
                    }
                    Text(
                        text = "Нет любимых фильмов...(",
                        color = Color.Black,
                        fontSize = 25.sp
                    )
                }

            }
        }
    }

}