@file:OptIn(ExperimentalGlideComposeApi::class)

package com.example.flavi.view.screens.searchMovie

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
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
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.flavi.model.data.database.map.toMovieCardEntity
import com.example.flavi.model.domain.entity.Genres
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieCard
import com.example.flavi.view.navigation.BottomNavigation
import com.example.flavi.view.screens.components.CheckFavoriteMovieList
import com.example.flavi.view.state.SearchMovieState
import com.example.flavi.view.ui.theme.MyIcons
import com.example.flavi.viewmodel.SearchMovieViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SearchMovie(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    onClickToMovieDetailScreen: (MovieCard) -> Unit,
    viewModel: SearchMovieViewModel = hiltViewModel()
) {
    val state = viewModel.stateSearchMovie.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            BottomNavigation.BottomNav(
                navHostController = navHostController
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
                                viewModel.apply {
                                    query.emit(value = currentQuery.value)
                                    saveHistorySearch(title = currentQuery.value)
                                }
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
                            viewModel.apply {
                                if (currentState.query.isEmpty()) {
                                    stateError.value = true
                                    color.value
                                } else {
                                    coroutineScope.launch {
                                        query.emit(currentState.query)
                                        saveHistorySearch(title = currentState.query)
                                    }
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
                                viewModel.apply {
                                    if (currentQuery.value.isEmpty()) {
                                        stateError.value = true
                                        color.value
                                    }
                                    if (currentQuery.value.isNotEmpty()) {
                                        query.emit(value = viewModel.currentQuery.value)
                                        saveHistorySearch(title = currentQuery.value)
                                    }
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
                    LazyColumn {
                        currentState.movie.forEach { movie ->
                            item {
                                val colorRating =
                                    if (movie.rating > "5.0") Color.Green else Color.Red

                                MovieCardComponent(
                                    onClickSaveMovie = {
                                        viewModel.saveMovieInTheFavorites(
                                            movie.copy(isFavorite = true)
                                        )
                                        val checkMovie = CheckFavoriteMovieList<MovieCard>()
                                        checkMovie.apply {
                                            list.add(movie)
                                            checkListMovie.value = true
                                        }
                                        viewModel.searchMovieInTheDB.value = true
                                    },
                                    onClickCheckingMovie = {
                                        coroutineScope.launch {
                                            if (viewModel.checkMovieByTitle(movieId = movie.filmId)) {
                                                viewModel.searchMovieInTheDB.value = true
                                            }
                                            if (!viewModel.checkMovieByTitle(movieId = movie.filmId)) {
                                                viewModel.searchMovieInTheDB.value = false
                                            }
                                        }
                                    },
                                    onClickRemoveMovie = {
                                        coroutineScope.launch {
                                            viewModel.apply {
                                                removeMovieFromFavorites(movieId = movie.filmId)
                                                searchMovieInTheDB.value = false
                                            }
                                        }
                                    },
                                    onClickGetMovieDetail = {
                                        onClickToMovieDetailScreen(movie)
                                    },
                                    searchMovie = viewModel.searchMovieInTheDB.value,
                                    movieImage = movie.posterUrlPreview,
                                    movieNameRu = movie.nameRu,
                                    nameOriginal = movie.nameEn,
                                    movieYear = movie.year,
                                    movieGenre = movie.genres.first().genre,
                                    movieCountrie = movie.countries.first().country,
                                    movieRating = movie.rating,
                                    movieColorRating = colorRating
                                )
                            }
                        }
                    }

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
                                viewModel.apply {
                                    query.emit(value = currentQuery.value)
                                }
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
                        currentState.listMovie.forEach { filterMovie ->
                            val colorRating = if (filterMovie.rating.imdb > 5.0)
                                Color.Green
                            else
                                Color.Red
                            val checkMovieObserver = CheckFavoriteMovieList<MovieCard>()
                            item {
                                MovieCardComponent(
                                    onClickSaveMovie = {
                                        coroutineScope.launch {
                                            viewModel.apply {
                                                saveMovieInTheFavorites(
                                                    mapFilterMovieCardToMovieCardEntity(filterMovie)
                                                )
                                                checkMovieObserver.list.add(
                                                    mapFilterMovieCardToMovieCardEntity(filterMovie)
                                                )
                                            }
                                            viewModel.searchMovieInTheDB.value = true
                                        }
                                    },
                                    onClickCheckingMovie = {
                                        coroutineScope.launch {
                                            if (viewModel.checkMovieByTitle(movieId = filterMovie.id)) {
                                                viewModel.searchMovieInTheDB.value = true
                                            }
                                            if (!viewModel.checkMovieByTitle(movieId = filterMovie.id)) {
                                                viewModel.searchMovieInTheDB.value = false
                                            }
                                        }
                                    },
                                    onClickRemoveMovie = {
                                        coroutineScope.launch {
                                            viewModel.apply {
                                                removeMovieFromFavorites(filterMovie.id)
                                                searchMovieInTheDB.value = false
                                            }
                                        }
                                    },
                                    onClickGetMovieDetail = {
                                        onClickToMovieDetailScreen(filterMovie.toMovieCardEntity())
                                    },
                                    searchMovie = viewModel.searchMovieInTheDB.value,
                                    movieImage = filterMovie.poster.previewUrl,
                                    movieNameRu = filterMovie.name,
                                    nameOriginal = filterMovie.alternativeName,
                                    movieYear = filterMovie.year.toString(),
                                    movieCountrie = filterMovie.countries.first().name,
                                    movieGenre = filterMovie.genres.first().name,
                                    movieRating = filterMovie.rating.imdb.toString(),
                                    movieColorRating = colorRating
                                )
                            }
                        }
                    }
                }

                is SearchMovieState.HistorySearchList -> {
                    coroutineScope.launch { viewModel.showHistorySearch() }
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
                    SearchHistoryList(viewModel = viewModel)
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
fun AlertDialogEstimateMovie(
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
                    listEstimate.forEach { element ->
                        item {
                            EstimateMovie(
                                textEstimate = element.toString(),
                                onClickToElement = onClickToElement,
                                onItemClick = {
                                    item.intValue = element
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

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun SearchMovieComponent(
    value: String,
    onValueChange: (String) -> Unit,
    viewModel: SearchMovieViewModel,
    onEmitValue: () -> Unit,
    color: TextFieldColors = TextFieldDefaults.colors()
) {
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = FocusRequester()
    val interactionSource = remember { MutableInteractionSource() }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 8.dp, end = 8.dp)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(10.dp)
            )
            .focusRequester(focusRequester),
        interactionSource = interactionSource,
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
        isError = viewModel.stateError.value,
        singleLine = true
    )
    Spacer(modifier = Modifier.height(15.dp))
    GetHistorySearchList(
        getListHistorySearchClick = {
            coroutineScope.launch {
                viewModel.emitToHistorySearchState()
            }
        }
    )
}

@Composable
private fun GetHistorySearchList(
    modifier: Modifier = Modifier,
    getListHistorySearchClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = getListHistorySearchClick
    ) {
        Text(
            text = "Посмотреть историю поиска",
            color = Color.Black
        )
    }
}

@Composable
fun CardHistorySearch(
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.cardColors(),
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        color = colors.containerColor
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun SearchHistoryList(
    modifier: Modifier = Modifier,
    viewModel: SearchMovieViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val listHistorySearch = remember { mutableStateOf(listOf<String>()) }
    coroutineScope.launch {
        listHistorySearch.value = viewModel.listHistorySearch.value
    }
    Log.d("Auth", viewModel.listHistorySearch.value.size.toString())
    LazyColumn(modifier = modifier.fillMaxSize()) {
        listHistorySearch.value.forEach {
            item {
                CardHistorySearch(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 8.dp, end = 8.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(10.dp)
                        ),
                    colors = CardDefaults.cardColors(containerColor = Color.Black)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, top = 16.dp, bottom = 16.dp)
                            .weight(1f)
                            .clickable {
                                coroutineScope.launch {
                                    viewModel.query.emit(it)
                                }
                            },
                        text = it,
                        color = Color.White
                    )
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.removeHistorySearch(title = it)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Очистить запрос",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MovieCardComponent(
    modifier: Modifier = Modifier,
    movieImage: String,
    movieNameRu: String,
    nameOriginal: String,
    movieYear: String,
    movieGenre: String,
    movieCountrie: String,
    movieRating: String,
    movieColorRating: Color,
    onClickSaveMovie: () -> Unit,
    onClickCheckingMovie: () -> Unit,
    onClickRemoveMovie: () -> Unit,
    onClickGetMovieDetail: () -> Unit,
    searchMovie: Boolean
) {
    val stateButtons = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    val expanded = remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(top = 50.dp)
            .clickable {
                onClickGetMovieDetail()
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
                model = movieImage,
                contentDescription = "Картинка фильма"
            )
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movieNameRu
                )
                Row {
                    Text(
                        text = nameOriginal
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = movieYear
                    )
                }
                Row {
                    Text(
                        text = movieCountrie
                    )
                    Spacer(modifier.width(16.dp))
                    Text(
                        text = movieGenre
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
                                        if (!searchMovie) {
                                            onClickSaveMovie()
                                        }
                                        if (searchMovie) {
                                            onClickRemoveMovie()
                                        }
                                        searchMovie
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
                        text = movieRating,
                        color = movieColorRating
                    )
                }
            }
        }
    }
}
