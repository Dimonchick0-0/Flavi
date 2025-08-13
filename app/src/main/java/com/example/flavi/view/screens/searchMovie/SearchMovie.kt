@file:OptIn(ExperimentalGlideComposeApi::class)

package com.example.flavi.view.screens.searchMovie

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.flavi.model.data.datasource.CountriesDTO
import com.example.flavi.model.data.datasource.GenresDTO
import com.example.flavi.model.domain.entity.Genres
import com.example.flavi.model.domain.entity.MovieCard
import com.example.flavi.view.navigation.BottomNavigation
import com.example.flavi.view.state.SearchMovieState
import com.example.flavi.view.ui.theme.MyIcons
import com.example.flavi.viewmodel.SearchMovieViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SearchMovie(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: SearchMovieViewModel = hiltViewModel()
) {
    val state = viewModel.stateSearchMovie.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        bottomBar = {
            BottomNavigation.BottomNav(
                navHostController = navHostController,
                containerColor = MaterialTheme.colorScheme.tertiary
            )
        },
        floatingActionButton = {
            FAB(viewModel = viewModel)

        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onBackground)
        ) {
            viewModel.setFiltersToMovies()
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
                        viewModel = viewModel,
                        color = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
                        )
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
                    val color = if (viewModel.currentQuery.value.isEmpty()) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    }
                    SearchMovieComponent(
                        value = viewModel.oldQuery.value,
                        onValueChange = {
                            viewModel.updateQuery(newQuery = it)
                            viewModel.stateError.value = false
                        },
                        viewModel = viewModel,
                        onEmitValue = {
                            coroutineScope.launch {
                                if (viewModel.currentQuery.value.isEmpty()) {
                                    viewModel.stateError.value = true
                                    color.value
                                }
                                if (viewModel.currentQuery.value.isNotEmpty()) {
                                    viewModel.query.emit(value = viewModel.currentQuery.value)
                                }
                            }
                        },
                        color = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedContainerColor = MaterialTheme.colorScheme.onBackground,
                            errorContainerColor = color
                        )
                    )
                    viewModel.processLoadMovie()
                    MovieCardComponent(
                        movieCard = currentState.movie,
                        onClickSaveMovie = {
                            viewModel.saveMovieInTheFavorites(
                                currentState.movie.copy(isFavorite = true)
                            )
                        },
                        onClickCheckingMovie = {
                            coroutineScope.launch {
                                if (viewModel.checkMovieByTitle(movieId = currentState.movie.id)) {
                                    viewModel.searchMovieInTheDB.value = true
                                }
                                if (!viewModel.checkMovieByTitle(movieId = currentState.movie.id)) {
                                    viewModel.searchMovieInTheDB.value = false
                                }
                            }
                        },
                        searchMovie = viewModel.searchMovieInTheDB.value
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

                is SearchMovieState.SwitchingFiltersState -> {
                    coroutineScope.launch { viewModel.processGetFilters(currentState.filter) }
                    SearchMovieComponent(
                        value = viewModel.oldQuery.value,
                        onValueChange = {
                            viewModel.updateQuery(it)
                            viewModel.stateError.value = false
                        },
                        onEmitValue = {
                            coroutineScope.launch {
                                viewModel.query.emit(value = viewModel.currentQuery.value)
                            }
                        },
                        viewModel = viewModel
                    )
                }

                is SearchMovieState.LoadListMovieWithFilters -> {
                    viewModel.processLoadMovieListWithFilters()
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

                    LazyColumn {
                        currentState.listMovie.docs.forEach {
                            item {
                                MovieCardComponent(
                                    movieCard = it,
                                    onClickSaveMovie = {
                                        coroutineScope.launch {
                                            viewModel.saveMovieInTheFavorites(
                                                it.copy(isFavorite = true)
                                            )
                                        }
                                    },
                                    onClickCheckingMovie = {
                                        coroutineScope.launch {
                                            if (viewModel.checkMovieByTitle(movieId = it.id)) {
                                                viewModel.searchMovieInTheDB.value = true
                                            }
                                            if (!viewModel.checkMovieByTitle(movieId = it.id)) {
                                                viewModel.searchMovieInTheDB.value = false
                                            }
                                        }
                                    },
                                    searchMovie = viewModel.searchMovieInTheDB.value
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
private fun EstimateMovie(
    modifier: Modifier = Modifier,
    textEstimate: String,
    onClickToElement: () -> Unit,
    onItemClick: () -> Unit
) {
    Box(modifier = modifier.clickable {
        onClickToElement()
        onItemClick()
    }) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = textEstimate,
            fontSize = 17.sp,
            color = Color.Black
        )
    }

}

@Composable
private fun AlertDialogEstimateMovie(
    modifier: Modifier = Modifier,
    changeStateShowDialog: () -> Unit,
    enabledButtons: Boolean,
    onClickToElement: () -> Unit,
) {
    val listEstimate = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val item = remember { mutableIntStateOf(0) }
    AlertDialog(
        modifier = modifier
            .width(350.dp),
        onDismissRequest = { changeStateShowDialog() },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyRow {
                    listEstimate.forEach { elemtent ->
                        item {
                            EstimateMovie(
                                textEstimate = elemtent.toString(),
                                onClickToElement = onClickToElement,
                                onItemClick = {
                                    item.intValue = elemtent
                                }
                            )
                        }

                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Ваша оценка: ${item.intValue}",
                    fontSize = 17.sp,
                    color = Color.Black
                )
            }
        },
        title = {
            Text(
                text = "Оценка фильма",
                fontSize = 17.sp,
                color = Color.Black
            )
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = changeStateShowDialog,
                    enabled = enabledButtons
                ) {
                    Text(
                        text = "Оценить",
                        color = Color.White
                    )
                }
            }
        }
    )

}

@Composable
private fun AlertDialogWhenOffAInternet(
    modifier: Modifier = Modifier,
    viewModel: SearchMovieViewModel
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = { viewModel.showDialog.value = false },
        text = {
            Text(
                text = "У вас отключён интернет...(",
                color = Color.Black
            )
        },
        title = {
            Text(
                text = "Уведомление об отключении интернета",
                color = Color.Black
            )
        },
        buttons = {
            Button(
                onClick = {
                    viewModel.showDialog.value = false
                }
            ) {
                Text(
                    text = "Я понял. Щас включу"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FAB(
    modifier: Modifier = Modifier,
    viewModel: SearchMovieViewModel
) {
    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val listOfGenres = listOf(
        Genres(idGenres = 0, name = "ужасы"),
        Genres(idGenres = 1, name = "драма"),
        Genres(idGenres = 2, name = "мелодрама")
    )
    FloatingActionButton(
        modifier = modifier,
        onClick = {
            if (viewModel.networkState.value) {
                showBottomSheet.value = true
            }
            if (!viewModel.networkState.value) {
                viewModel.showDialog.value = true
            }
        }) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = MyIcons.Settings,
                contentDescription = "Фильтры для фильмов"
            )
            Text(
                text = "Применить фильтры"
            )
        }
    }

    if (viewModel.showDialog.value) {
        AlertDialogWhenOffAInternet(viewModel = viewModel)
    }

    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet.value = false
            },
            sheetState = sheetState
        ) {
            LazyColumn {
                listOfGenres.forEach {
                    item {
                        ListGenres(
                            onClickToElement = {
                                coroutineScope.launch { viewModel.filters.emit(value = it.name) }
                            },
                            genres = it
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ListGenres(
    modifier: Modifier = Modifier,
    genres: Genres,
    onClickToElement: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.White)
            .clickable { onClickToElement() }
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = genres.name,
            fontSize = 16.sp
        )
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
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(10.dp)
            ),
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(text = "Поиск фильмов...")
        },
        leadingIcon = {
            IconButton(onClick = {
                viewModel.clearQuery()
            }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Очистить запрос"
                )
            }
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

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MovieCardComponent(
    modifier: Modifier = Modifier,
    movieCard: MovieCard,
    onClickSaveMovie: () -> Unit,
    onClickCheckingMovie: () -> Unit,
    searchMovie: Boolean
) {
    val stateButtons = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    val expanded = remember { mutableStateOf(false) }
    val colorRating = if (movieCard.rating.imdb > 5.0) Color.Green else Color.Red
    Card(
        modifier = modifier
            .padding(top = 50.dp)
            .clickable {
                Log.d("Auth", movieCard.id.toString())
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                modifier = Modifier
                    .size(100.dp)
                    .fillMaxHeight(),
                model = movieCard.poster.url,
                contentDescription = "Картинка фильма"
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movieCard.name
                )
                Row {
                    Text(
                        text = movieCard.alternativeName
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = movieCard.year.toString()
                    )
                }
                Row {
                    Text(
                        text = movieCard.countries.toCountrie().name
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = movieCard.genres.toGenresDTO().name
                    )
                }
            }
            Row {
                Column(
                    modifier = Modifier.width(32.dp)
                ) {
                    IconButton(
                        modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                        onClick = {
                            onClickCheckingMovie()
                            expanded.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = ""
                        )
                    }
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = {
                            expanded.value = false
                        }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Like") },
                            onClick = {},
                            leadingIcon = {
                                IconButton(
                                    onClick = {
                                        onClickSaveMovie()
                                        searchMovie
//                                        viewModel.searchMovieInTheDB.value = true
                                    }
                                ) {
                                    if (searchMovie) {
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = ""
                                        )
                                    }
                                    if (!searchMovie) {
                                        Icon(
                                            imageVector = Icons.Default.FavoriteBorder,
                                            contentDescription = ""
                                        )
                                    }
                                }
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Оценить") },
                            onClick = {},
                            leadingIcon = {
                                IconButton(
                                    onClick = {
                                        showDialog.value = true
                                    }
                                ) {
                                    Icon(
                                        imageVector = MyIcons.Star,
                                        contentDescription = ""
                                    )
                                    if (showDialog.value) {
                                        AlertDialogEstimateMovie(
                                            changeStateShowDialog = {
                                                showDialog.value = false
                                                stateButtons.value = false
                                            },
                                            enabledButtons = stateButtons.value,
                                            onClickToElement = {
                                                stateButtons.value = true
                                            }
                                        )
                                    }
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = movieCard.rating.imdb.toString(),
                        color = colorRating
                    )
                }
            }
        }
    }
}

private fun List<GenresDTO>.toGenresDTO(): GenresDTO {
    return this.elementAt(0)
}

private fun List<CountriesDTO>.toCountrie(): CountriesDTO {
    return this.elementAt(0)
}