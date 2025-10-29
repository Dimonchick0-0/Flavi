@file:OptIn(ExperimentalGlideComposeApi::class)

package com.example.flavi.view.screens.searchMovie

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
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
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.TextButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Dp
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
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.OrderFilterMovie
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.SearchActor
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.TypeFilterMovie
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
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            BottomNavigation.BottomNav(navHostController = navHostController)
        },
        floatingActionButton = {
            FAB(viewModel = viewModel)
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (val currentState = state.value) {

                SearchMovieState.Initial -> {
                    viewModel.processInitial()
                }

                SearchMovieState.NotFound -> {
                    SearchMovieComponent(viewModel = viewModel)
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
                    SearchMovieComponent(viewModel = viewModel)
                }

                is SearchMovieState.LoadMovieAndActors -> {
                    SearchMovieComponent(viewModel = viewModel)

                    LazyColumn {
                        item {
                            if (viewModel.checkActor.value) {
                                ActorsList(searchActor = currentState.searchActor)
                            }
                        }
                        currentState.movie.forEach { movie ->
                            item {
                                val colorRating =
                                    if (movie.rating > "5.0") Color.Green else Color.Red

                                movie.nameEn?.let {
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

                }

                is SearchMovieState.ConnectToTheNetwork -> {
                    viewModel.processConnectToTheNetwork()
                }

                is SearchMovieState.NetworkShutdown -> {
                    viewModel.processNetworkShutdownState()
                }

                is SearchMovieState.NotificationOfInternetLoss -> {
                    viewModel.processNotificationOfInternetLoss()
                    SearchMovieComponent(viewModel = viewModel)
                    Spacer(modifier = Modifier.height(50.dp))
                    Text(
                        text = viewModel.notificationOfInternetLoss.value,
                        color = Color.Black
                    )
                }

                is SearchMovieState.LoadListMovieWithFilters -> {
                    SearchMovieComponent(viewModel = viewModel)

                    LazyColumn {
                        if (viewModel.checkLoadFilterMovie.value
                            && currentState.listMovie.isNotEmpty()) {

                            currentState.listMovie
                                .filter { it.ratingImdb != null }
                                .forEach { filterMovie ->
                                    val color = filterMovie.ratingImdb?.let { colorRating ->
                                        if (colorRating > 5.0)
                                            Color.Green
                                        else
                                            Color.Red
                                    }

                                    val checkMovieObserver = CheckFavoriteMovieList<MovieCard>()
                                    item {
                                        filterMovie.nameOriginal?.let {

                                            MovieCardComponent(
                                                onClickSaveMovie = {
                                                    coroutineScope.launch {
                                                        viewModel.apply {
                                                            saveMovieInTheFavorites(
                                                                mapFilterMovieCardToMovieCardEntity(
                                                                    filterMovie
                                                                )
                                                            )
                                                            checkMovieObserver.list.add(
                                                                mapFilterMovieCardToMovieCardEntity(
                                                                    filterMovie
                                                                )
                                                            )
                                                        }
                                                        viewModel.searchMovieInTheDB.value = true
                                                    }
                                                },
                                                onClickCheckingMovie = {
                                                    coroutineScope.launch {
                                                        if (viewModel.checkMovieByTitle(
                                                                movieId = filterMovie.kinopoiskId
                                                            )
                                                        ) {
                                                            viewModel.searchMovieInTheDB.value = true
                                                        }
                                                        if (!viewModel.checkMovieByTitle(
                                                                movieId = filterMovie.kinopoiskId
                                                            )
                                                        ) {
                                                            viewModel.searchMovieInTheDB.value = false
                                                        }
                                                    }
                                                },
                                                onClickRemoveMovie = {
                                                    coroutineScope.launch {
                                                        viewModel.apply {
                                                            removeMovieFromFavorites(filterMovie.kinopoiskId)
                                                            searchMovieInTheDB.value = false
                                                        }
                                                    }
                                                },
                                                onClickGetMovieDetail = {
                                                    onClickToMovieDetailScreen(filterMovie.toMovieCardEntity())
                                                },
                                                searchMovie = viewModel.searchMovieInTheDB.value,
                                                movieImage = filterMovie.posterUrlPreview,
                                                movieNameRu = filterMovie.nameRu,
                                                nameOriginal = filterMovie.nameOriginal,
                                                movieYear = filterMovie.year.toString(),
                                                movieCountrie = filterMovie.countries.first().country,
                                                movieGenre = filterMovie.genres.first().genre,
                                                movieRating = filterMovie.ratingImdb.toString(),
                                                movieColorRating = color!!
                                            )
                                        }
                                    }
                                }
                        }
                    }
                }

                SearchMovieState.GenresMovie -> {
                    GenresMovie(viewModel = viewModel)
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun SearchMovieComponent(
    modifier: Modifier = Modifier,
    viewModel: SearchMovieViewModel,
) {
    val coroutineScope = rememberCoroutineScope()
    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()

    val inputField = @Composable {
        SearchBarDefaults.InputField(
            modifier = modifier.fillMaxWidth(),
            searchBarState = searchBarState,
            textFieldState = textFieldState,
            onSearch = {
                coroutineScope.launch {
                    searchBarState.animateToCollapsed()
                }
            },
            placeholder = {
                Text(
                    modifier = Modifier.clearAndSetSemantics { }, text = "Поиск..."
                )
            },
            leadingIcon = {
                if (searchBarState.currentValue == SearchBarValue.Expanded) {
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(
                            TooltipAnchorPosition.Above
                        ),
                        tooltip = {
                            PlainTooltip { Text(text = "Back") }
                        },
                        state = rememberTooltipState()
                    ) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    searchBarState.animateToCollapsed()
                                }
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(28.dp),
                                imageVector = MyIcons.KeyboardArrowLeft,
                                contentDescription = ""
                            )
                        }
                    }
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            viewModel.apply {
                                query.emit(textFieldState.text.toString())
                                gettingTheEnteredQuery()
                                saveHistorySearch(textFieldState.text.toString())
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = MyIcons.Search,
                        contentDescription = ""
                    )
                }
            }
        )
    }
    Spacer(modifier = Modifier.height(height = 16.dp))
    SearchBar(
        state = searchBarState,
        inputField = inputField
    )
    ExpandedFullScreenSearchBar(
        state = searchBarState,
        inputField = inputField
    ) {
        Spacer(modifier = Modifier.height(height = 8.dp))
        Text(
            text = "История поиска...",
            color = Color.White
        )
        Spacer(modifier = Modifier.height(height = 8.dp))
        coroutineScope.launch { viewModel.showHistorySearch() }
        if (viewModel.listHistorySearch.value.isNotEmpty()) {
            LazyColumn {
                viewModel.listHistorySearch.value.forEach {
                    item {

                        HistoryList(
                            onEmittedText = {
                                coroutineScope.launch {
                                    viewModel.apply {
                                        query.emit(it)
                                        gettingTheEnteredQuery()
                                    }
                                }
                            },
                            clearHistorySearchClick = {
                                coroutineScope.launch {
                                    viewModel.removeHistorySearch(it)
                                }
                            },
                            text = it
                        )
                    }
                }
            }
        }

    }
}

private fun Modifier.bottomBorder(strokeWidth: Dp, color: Color) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }

        Modifier.drawBehind {
            val width = size.width
            val height = size.height - strokeWidthPx / 2

            drawLine(
                color = color,
                start = Offset(x = 0f, y = height),
                end = Offset(x = width, y = height),
                strokeWidth = strokeWidthPx
            )
        }
    }
)

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun HistoryList(
    modifier: Modifier = Modifier,
    onEmittedText: () -> Unit,
    clearHistorySearchClick: () -> Unit,
    text: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .bottomBorder(strokeWidth = 2.dp, color = Color.White)
    ) {
        TextButton(
            modifier = Modifier.weight(1f),
            onClick = onEmittedText
        ) {
            Text(
                text = text,
                color = Color.White
            )
        }
        IconButton(onClick = clearHistorySearchClick) {
            Icon(
                imageVector = MyIcons.Cleaning_services,
                contentDescription = "",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun ActorsList(
    modifier: Modifier = Modifier,
    searchActor: SearchActor
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            GlideImage(
                modifier = Modifier.size(100.dp),
                model = searchActor.posterUrl,
                contentDescription = ""
            )
            Column {
                Text(
                    text = searchActor.nameRu
                )
                Text(
                    text = searchActor.nameEn
                )
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

    FloatingActionButton(
        modifier = modifier,
        onClick = {
            if (viewModel.networkState.value) {
                showBottomSheet.value = true
            }
            if (!viewModel.networkState.value) {
                viewModel.showDialog.value = true
            }
        }
    ) {
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
            Column {
                TextButton(
                    onClick = {
                        viewModel.resetFilters()
                    }
                ) {
                    Text(
                        text = "Сбросить фильтры",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                TextButton(
                    onClick = { viewModel.getMovieGenres() }
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        text = "Жанры",
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (viewModel.genreMovie.value.isEmpty()) {
                        Text(
                            text = "Все жанры",
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Text(
                            text = viewModel.genreMovie.value,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                }
                SortedMovie(viewModel = viewModel)
                SortedMovieType(viewModel = viewModel)
            }
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    coroutineScope.launch {
                        viewModel.apply {

                            if (order != OrderFilterMovie.NOT_SELECTED
                                && typeMovie != TypeFilterMovie.NOT_SELECTED
                                && genreMovie.value != "") {

                                val list = getMovieByFilter(
                                    order = order.toString(),
                                    type = typeMovie.toString(),
                                    keyword = genreMovie.value
                                )
                                setStateLoadFilterMovie(filterMovie = list)

                            }

                            if (order == OrderFilterMovie.NOT_SELECTED
                                && typeMovie == TypeFilterMovie.NOT_SELECTED
                                && genreMovie.value != "") {
                                val filterMovieList = getMovieByFiltersOnlyByName(
                                    genreMovie.value
                                )
                                setStateLoadFilterMovie(filterMovie = filterMovieList)
                            }

                        }

                    }

                }
            ) {
                Text(
                    text = "Применить фильтры",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun SortedMovieType(viewModel: SearchMovieViewModel) {

    Spacer(modifier = Modifier.height(height = 8.dp))
    Text(
        modifier = Modifier.padding(start = 8.dp),
        text = "Выбрать тип",
        color = MaterialTheme.colorScheme.primary,
        fontSize = 18.sp
    )
    Spacer(modifier = Modifier.height(height = 8.dp))
    Column {

        GetSortedMovieType(
            text = "Всё",
            viewModel = viewModel
        )
        GetSortedMovieType(
            text = "Фильмы",
            viewModel = viewModel
        )
        GetSortedMovieType(
            text = "ТелеШоу",
            viewModel = viewModel
        )
        GetSortedMovieType(
            text = "ТелеСериал",
            viewModel = viewModel
        )
        GetSortedMovieType(
            text = "МиниСериал",
            viewModel = viewModel
        )

    }
}

@Composable
private fun GetSortedMovieType(
    modifier: Modifier = Modifier,
    viewModel: SearchMovieViewModel,
    text: String
) {

    var typeMovie by remember { mutableStateOf(TypeFilterMovie.NOT_SELECTED) }
    var checked by remember { mutableStateOf(false) }

    if (viewModel.checkResetFilters.value) {
        checked = false
    }

    Row {
        Text(
            modifier = modifier
                .weight(1f)
                .padding(start = 8.dp),
            text = text,
            color = MaterialTheme.colorScheme.primary
        )
        Switch(
            checked = checked,
            onCheckedChange = {
                viewModel.checkResetFilters.value = false
                viewModel.checkLoadFilterMovie.value = false
                checked = it
                when (text) {
                    "Всё" -> {
                        if (it) {
                            typeMovie = TypeFilterMovie.ALL
                        }
                    }

                    "Фильмы" -> {
                        if (it) {
                            typeMovie = TypeFilterMovie.FILM
                        }
                    }

                    "ТелеШоу" -> {
                        if (it) {
                            typeMovie = TypeFilterMovie.TV_SHOW
                        }
                    }

                    "ТелеСериал" -> {
                        if (it) {
                            typeMovie = TypeFilterMovie.TV_SERIES
                        }
                    }

                    "МиниСериал" -> {
                        if (it) {
                            typeMovie = TypeFilterMovie.MINI_SERIES
                        }
                    }
                }
                viewModel.typeMovie = typeMovie
            }
        )
    }

}

@Composable
private fun SortedMovie(
    modifier: Modifier = Modifier,
    viewModel: SearchMovieViewModel
) {
    Spacer(modifier = Modifier.height(height = 8.dp))
    Text(
        modifier = Modifier.padding(start = 8.dp),
        text = "Сортировать по",
        color = MaterialTheme.colorScheme.primary,
        fontSize = 18.sp
    )
    Spacer(modifier = Modifier.height(height = 8.dp))
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SortedTextMovie(
            text = "Рейтингу",
            viewModel = viewModel
        )
        SortedTextMovie(
            text = "Популярности",
            viewModel = viewModel
        )
        SortedTextMovie(
            text = "Дате",
            viewModel = viewModel
        )
    }
}

@Composable
private fun SortedTextMovie(
    modifier: Modifier = Modifier,
    viewModel: SearchMovieViewModel,
    text: String
) {
    val colorIsFocused = remember { mutableStateOf(Color.White) }
    val isFocused = remember { mutableStateOf(false) }
    val requester = remember { FocusRequester() }
    var order by remember { mutableStateOf(OrderFilterMovie.NOT_SELECTED) }

    if (viewModel.checkResetFilters.value) {
        isFocused.value = false
    }

    Box(
        modifier = modifier
            .clickable {
                viewModel.checkResetFilters.value = false
                viewModel.checkLoadFilterMovie.value = false
                requester.requestFocus(focusDirection = FocusDirection.Enter)
                when (text) {
                    "Рейтингу" -> order = OrderFilterMovie.RATING
                    "Популярности" -> order = OrderFilterMovie.NUM_VOTE
                    "Дате" -> order = OrderFilterMovie.YEAR
                }
                viewModel.order = order
            }
            .focusRequester(requester)
            .onFocusChanged {
                colorIsFocused.value = if (it.isFocused) Color.Black else Color.White
                isFocused.value = it.isFocused
            }
            .focusable()
            .bottomBorder(
                strokeWidth = 2.dp,
                color = if (isFocused.value) Color.White else Color.Black
            )
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun GenresMovie(
    modifier: Modifier = Modifier,
    viewModel: SearchMovieViewModel
) {

    val coroutineScope = rememberCoroutineScope()
    val listOfGenres = listOf(
        Genres(idGenres = 0, name = "ужасы"),
        Genres(idGenres = 1, name = "драма"),
        Genres(idGenres = 2, name = "мелодрама"),
        Genres(idGenres = 3, name = "триллер")
    )

    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        listOfGenres.forEach {
            item {
                ListGenres(
                    onClickToElement = {
                        coroutineScope.launch {
                            viewModel.apply {
                                genreMovie.value = it.name
                                processInitial()
                            }
                        }
                    },
                    genres = it
                )
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
            .bottomBorder(strokeWidth = 2.dp, color = Color.White)
            .clickable { onClickToElement() }
    ) {
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = genres.name,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary
        )
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
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
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
                    text = movieNameRu,
                    color = MaterialTheme.colorScheme.primary
                )
                Row {
                    Text(
                        text = nameOriginal,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = movieYear,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Row {
                    Text(
                        text = movieCountrie,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier.width(16.dp))
                    Text(
                        text = movieGenre,
                        color = MaterialTheme.colorScheme.primary
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
                            imageVector = MyIcons.EllipsisVertical,
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
                                    }
                                ) {
                                    if (searchMovie) {
                                        Icon(
                                            imageVector = MyIcons.Heart_minus,
                                            contentDescription = ""
                                        )
                                    }
                                    if (!searchMovie) {
                                        Icon(
                                            imageVector = MyIcons.Heart_plus,
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
