package com.example.flavi.view.screens.favoritesMovies

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
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
import com.example.flavi.view.state.FavoritesMoviesState
import com.example.flavi.viewmodel.FavoritesMoviesViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesMoviesViewModel = hiltViewModel(),
    navHostController: NavHostController
) {

    val state = viewModel.stateFavoritesMovies.collectAsStateWithLifecycle()

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomNavigation.BottomNav(
                navHostController = navHostController,
                containerColor = MaterialTheme.colorScheme.tertiary,
                onClickToItemNavigation = {}
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            item(key = null) {
                Row {
                    when (val currentState = state.value) {
                        FavoritesMoviesState.InitialState -> {
                            viewModel.processInitialState()
                        }
                        is FavoritesMoviesState.LoadFavoritesMovies -> {
                            viewModel.processLoadFavoritesMovies()
                            Column {
                                currentState.movieCard.forEach {
                                    Card {
                                        Text(
                                            text = it.name,
                                            color = Color.Black,
                                            fontSize = 20.sp
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}