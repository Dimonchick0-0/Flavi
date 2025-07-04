@file:OptIn(ExperimentalGlideComposeApi::class)

package com.example.flavi.view.screens.searchMovie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.flavi.view.navigation.BottomNavigation
import com.example.flavi.viewmodel.SearchMovieState
import com.example.flavi.viewmodel.SearchMovieViewModel
import kotlinx.coroutines.launch

@Composable
fun SearchMovie(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: SearchMovieViewModel = hiltViewModel(),
    onClickToProfileUser: () -> Unit
) {
    val state = viewModel.stateSearchMovie.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            BottomNavigation.BottomNav(
                navHostController = navHostController,
                onClickToItemNavigation = onClickToProfileUser
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
                SearchMovieState.Initial -> {
                    viewModel.processInitial()
                }

                SearchMovieState.NotFound -> {
                    val coroutineScope = rememberCoroutineScope()
                    SearchMovieComponent(
                        value = viewModel.oldQuery.value,
                        onValueChange = {
                            viewModel.processNotFoundMovie(query = it)
                        },
                        onEmitValue = {
                            coroutineScope.launch {
                                viewModel.query.emit(value = viewModel.currentQuery.value)
                            }
                        },
                        viewModel = viewModel
                    )
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "не найден фильм",
                            color = Color.Black
                        )
                    }
                }

                is SearchMovieState.InputQuery -> {
                    val color = if (currentState.query.isEmpty()) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    }
                    val coroutineScope = rememberCoroutineScope()
                    SearchMovieComponent(
                        value = currentState.query,
                        onValueChange = {
                            viewModel.processQuery(it)
                            viewModel.stateError.value = false
                            color.value
                        },
                        viewModel = viewModel,
                        onEmitValue = {
                            if (currentState.query.isEmpty()) {
                                viewModel.stateError.value = true
                                color.value
                            } else {
                                coroutineScope.launch {
                                    viewModel.query.emit(currentState.query)
                                }
                            }
                        },
                        color = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
                            errorContainerColor = color
                        )
                    )

                }

                is SearchMovieState.LoadMovie -> {
                    val coroutineScope = rememberCoroutineScope()
                    SearchMovieComponent(
                        value = viewModel.oldQuery.value,
                        onValueChange = {
                            viewModel.updateQuery(newQuery = it)
                        },
                        viewModel = viewModel,
                        onEmitValue = {
                            coroutineScope.launch {
                                viewModel.query.emit(value = viewModel.currentQuery.value)
                            }
                        },
                        color = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedContainerColor = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    viewModel.processLoadMovie()
                    MovieCard(
                        logo = currentState.logo.url,
                        name = currentState.name,
                        year = currentState.year,
                        description = currentState.description
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchMovieComponent(
    value: String,
    onValueChange: (String) -> Unit,
    viewModel: SearchMovieViewModel,
    onEmitValue: () -> Unit,
    color: TextFieldColors = TextFieldDefaults.colors()
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(text = "Поиск фильмов...")
        },
        trailingIcon = {
            IconButton(onClick = onEmitValue) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = ""
                )
            }
        },
        colors = color,
        isError = viewModel.stateError.value
    )
}

@Composable
private fun MovieCard(
    logo: String,
    name: String,
    year: Int,
    description: String
) {
    Card(
        modifier = Modifier.padding(top = 50.dp)
    ) {
        Column {
            GlideImage(
                modifier = Modifier
                    .size(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,
                model = logo,
                contentDescription = "Изображение фильма"
            )
            Row {
                Text(
                    text = name
                )
                Text(
                    text = year.toString()
                )
            }
            Text(
                text = description,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}