package com.example.flavi.view.screens.favorite

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
import com.example.flavi.view.navigation.BottomNavigation
import com.example.flavi.view.screens.searchMovie.MovieCardComponent
import com.example.flavi.viewmodel.FavoriteScreenState
import com.example.flavi.viewmodel.FavoriteScreenViewModel
import com.example.flavi.viewmodel.SearchMovieViewModel
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteScreenViewModel = hiltViewModel(),
    navHostController: NavHostController
) {

    val state = viewModel.favoriteState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            BottomNavigation.BottomNav(
                navHostController = navHostController,
                containerColor = MaterialTheme.colorScheme.tertiary
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
                    viewModel.processFavoriteMovieList()
                    viewModel.getMovieCard()
                    currentState.movieList.forEach {
                        MovieCardComponent(
                            movieCard = it,
                            onClickSaveMovie = {
                                viewModel.saveMovieToFavorite(it)
                            },
                            onClickCheckingMovie = {
                                coroutineScope.launch {
                                    if (viewModel.checkingMovieInFavorite(it.id)) {
                                        viewModel.checkMovieInFavorite.value = true
                                    }
                                    if (!viewModel.checkingMovieInFavorite(movieId = it.id)) {
                                        viewModel.checkMovieInFavorite.value = false
                                    }
                                }
                            },
                            searchMovie = viewModel.checkMovieInFavorite.value
                        )
                    }
                }
            }
        }
    }

}