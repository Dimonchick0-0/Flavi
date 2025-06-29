package com.example.flavi.presentation.screens.searchMovie

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.flavi.presentation.navigation.Screens
import com.example.flavi.presentation.screens.FlaviBottomNavigation
import kotlinx.coroutines.launch

@Composable
fun SearchMovie(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: SearchMovieViewModel = hiltViewModel()
) {
    val state = viewModel.stateSearchMovie.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            val listNavigation = listOf(
                FlaviBottomNavigation("Поиск", Screens.SearchMovie, Icons.Filled.Search),
                FlaviBottomNavigation("Аккаунт", Screens.Profile, Icons.Filled.AccountCircle)
            )
            NavigationBar {
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                listNavigation.forEach { flaviNavItem ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = flaviNavItem.icon,
                                contentDescription = flaviNavItem.name
                            )
                        },
                        label = { Text(text = flaviNavItem.name) },
                        selected = navBackStackEntry?.destination?.hierarchy?.any {
                            it.hasRoute(flaviNavItem.route::class)
                        } == true,
                        onClick = {
                            navHostController.navigate(flaviNavItem.route) {
                                popUpTo(navHostController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onBackground)
        ) {
            when (val currentState = state.value) {

                SearchMovieState.Error -> {}
                SearchMovieState.Initial -> {
                    viewModel.processInitial()
                }

                is SearchMovieState.InputQuery -> {
                    val coroutineScope = rememberCoroutineScope()
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        value = currentState.query,
                        onValueChange = { viewModel.processQuery(it) },
                        placeholder = {
                            Text(text = "Поиск фильмов...")
                        },
                        trailingIcon = {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    viewModel.query.emit(currentState.query)
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = ""
                                )
                            }
                        }
                    )
                }
            }

        }

    }
}