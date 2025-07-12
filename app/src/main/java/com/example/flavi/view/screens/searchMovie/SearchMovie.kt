@file:OptIn(ExperimentalGlideComposeApi::class)

package com.example.flavi.view.screens.searchMovie

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.flavi.model.data.datasource.CountriesDTO
import com.example.flavi.model.data.datasource.GenresDTO
import com.example.flavi.model.data.datasource.PosterDTO
import com.example.flavi.model.data.datasource.RatingDTO
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
    val coroutineScope = rememberCoroutineScope()
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
                        name = currentState.name,
                        alternativeName = currentState.alternativeName,
                        year = currentState.year,
                        poster = currentState.posterDTO.url,
                        rating = currentState.ratingDTO.imdb,
                        genres = currentState.genresDTO.name,
                        countrie = currentState.countriesDTO.name
                    )
                }

                is SearchMovieState.ConnectToTheNetwork -> {
                    viewModel.processConnectToTheNetwork()
                }

                is SearchMovieState.NetworkShutdown -> {
                    viewModel.processNetworkShutdownState()
                }

                is SearchMovieState.NotificationOfInternetLoss -> {
                    viewModel.processNotificationOfInternetLoss()
                    SearchMovieComponent(
                        value = viewModel.oldQuery.value,
                        onValueChange = {
                            viewModel.updateQuery(it)
                        },
                        onEmitValue = {
                            coroutineScope.launch {
                                viewModel.query.emit(value = viewModel.currentQuery.value)
                            }
                        },
                        viewModel = viewModel
                    )
                    Spacer(modifier = Modifier.height(50.dp))
                    Text(
                        text = viewModel.notificationOfInternetLoss.value,
                        color = Color.Black
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
    name: String,
    alternativeName: String,
    year: Int,
    poster: String,
    rating: Float,
    genres: String,
    countrie: String
) {
    val colorRating = if (rating > 5.0) Color.Green else Color.Red
    Card(
        modifier = Modifier.padding(top = 50.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                modifier = Modifier
                    .size(100.dp)
                    .fillMaxHeight(),
                model = poster,
                contentDescription = "Картинка фильма"
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name
                )
                Row {
                    Text(
                        text = alternativeName
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = year.toString()
                    )
                }
                Row {
                    Text(
                        text = countrie
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = genres
                    )
                }
            }
            Row {
                Text(
                    text = rating.toString(),
                    color = colorRating
                )
            }
        }
    }
}