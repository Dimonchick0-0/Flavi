package com.example.flavi.view.screens.movieDetail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.TextButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.flavi.model.data.datasource.rental.RentalMovie
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Actor
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieCard
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MoviesSequelAndPrequel
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Poster
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Review
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.ReviewType
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.SimilarMovie
import com.example.flavi.view.screens.components.CheckFavoriteMovieList
import com.example.flavi.view.screens.searchMovie.AlertDialogEstimateMovie
import com.example.flavi.view.state.MovieDetailState
import com.example.flavi.view.ui.theme.MyIcons
import com.example.flavi.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MovieDetail(
    modifier: Modifier = Modifier,
    filmId: Int,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    loadAllImageMovieClick: () -> Unit,
    getAwardsByMovie: () -> Unit,
    getReviewsByMovie: () -> Unit,
    getNewMovieById: (Int) -> Unit
) {

    val state = viewModel.movieDetailState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()
    
    loadMovies(
        viewModel = viewModel,
        filmId = filmId
    )

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onBackground),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                when (val currentState = state.value) {

                    is MovieDetailState.LoadMovieDetail -> {
                        coroutineScope.launch {
                            viewModel.apply {
                                processLoadMovieDetailState()
                                if (checkMovieInFavorite(currentState.movie.kinopoiskId)) {
                                    checkMovieInFavorite.value = true
                                }
                                if (!checkMovieInFavorite(currentState.movie.kinopoiskId)) {
                                    checkMovieInFavorite.value = false
                                }
                            }
                        }
                        MovieDetailComponent(
                            movieDetail = currentState.movie,
                            onClickRemoveMovieFromFavorite = {
                                coroutineScope.launch {
                                    viewModel.apply {
                                        removeMovieFromFavorite(currentState.movie.kinopoiskId)
                                        checkMovieInFavorite.value = false
                                        setStatusMovieFavoriteDuringRemove(currentState.movie)
                                    }
                                }
                            },
                            onClickSaveToFavoriteMovie = {
                                val movieList = CheckFavoriteMovieList<MovieCard>()
                                coroutineScope.launch {
                                    viewModel.apply {
                                        saveMovieToFavorite(currentState.movie)
                                        movieList.list.add(
                                            mapMovieDetailToMovieCard(currentState.movie)
                                        )
                                        checkMovieInFavorite.value = true
                                    }
                                }
                            },
                            getAwardsByMovie = getAwardsByMovie,
                            checkMovieInFavorite = viewModel.checkMovieInFavorite.value,
                            viewModel = viewModel,
                            filmId = filmId,
                            loadAllImageMovieClick = loadAllImageMovieClick,
                            actors = currentState.actors,
                            rentalMovie = currentState.rental,
                            reviewList = currentState.review,
                            getAllReviewsClick = getReviewsByMovie,
                            similar = currentState.similars,
                            sequelAndPrequel = currentState.sequelsAndPreques,
                            getNewMovieById = getNewMovieById
                        )

                    }
                }
            }
        }

    }
}

private fun loadMovies(
    viewModel: MovieDetailViewModel,
    filmId: Int
) {
    viewModel.apply {
        getMovieById(filmId)
        getActors(filmId)
        getRental(filmId)
        getReviews(filmId)
        getSimilars(filmId)
        getSequelsAndPrequelsMovie(filmId)
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MovieDetailComponent(
    movieDetail: MovieDetail,
    viewModel: MovieDetailViewModel,
    filmId: Int,
    actors: List<Actor>,
    rentalMovie: Set<RentalMovie>,
    reviewList: List<Review>,
    similar: List<SimilarMovie>,
    sequelAndPrequel: List<MoviesSequelAndPrequel>,
    onClickSaveToFavoriteMovie: () -> Unit,
    onClickRemoveMovieFromFavorite: () -> Unit,
    loadAllImageMovieClick: () -> Unit,
    getAwardsByMovie: () -> Unit,
    getAllReviewsClick: () -> Unit,
    getNewMovieById: (Int) -> Unit,
    checkMovieInFavorite: Boolean
) {
    val maxLines = remember { mutableIntStateOf(3) }
    val coroutineScope = rememberCoroutineScope()
    val listTypeImage = listOf("Кадры", "Постеры", "Фан-арты")
    val listPoster = remember { mutableListOf<Poster>() }
    val checkImageInList = remember { mutableStateOf(false) }

    GlideImage(
        modifier = Modifier.height(300.dp),
        model = movieDetail.posterUrl,
        contentDescription = "Картинка"
    )
    Text(
        modifier = Modifier.padding(top = 10.dp),
        text = movieDetail.nameRu,
        color = Color.Black,
        fontSize = 24.sp
    )
    Text(
        text = movieDetail.nameOriginal,
        fontSize = 16.sp,
        color = Color.Black
    )
    Row {
        Text(
            text = movieDetail.year.toString() + " ",
            fontSize = 16.sp,
            color = Color.Black
        )
        movieDetail.genres.forEach {
            Text(
                text = it.genre + " ",
                color = Color.Black
            )
        }
    }
    Row {
        movieDetail.countries.forEach {
            Text(
                text = it.country + " ",
                color = Color.Black
            )
        }
    }
    OpenDescriptionMovie(
        movieDetail = movieDetail,
        isOpenDescription = {
            if (maxLines.intValue == maxLines.intValue) {
                maxLines.intValue = Int.MAX_VALUE
            }
        },
        isClosedDescription = {
            if (maxLines.intValue == Int.MAX_VALUE) {
                maxLines.intValue = 3
            }
        },
        maxLines = maxLines.intValue
    )
    Spacer(modifier = Modifier.height(25.dp))
    Rating(movieDetail = movieDetail)
    Spacer(modifier = Modifier.height(25.dp))
    CardFunctionMovieComponent(
        onClickRemoveMovieFromFavorite = onClickRemoveMovieFromFavorite,
        onClickSaveToFavoriteMovie = onClickSaveToFavoriteMovie,
        checkMovieInFavorite = checkMovieInFavorite
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Изображения",
        color = Color.Black
    )
    Spacer(modifier = Modifier.height(16.dp))
    ComponentMovieDetail.CardInFilterQueryImage(
        listCardFilterImageQuery = listTypeImage,
        loadImageClick = {
            coroutineScope.launch {
                if (listPoster.isNotEmpty()) {
                    listPoster.clear()
                    checkImageInList.value = false
                }
                if (listPoster.isEmpty()) {
                    viewModel.loadImageMovie(id = filmId, type = it).forEach {
                        listPoster.add(it)
                        checkImageInList.value = true
                    }
                }
            }
        },
        content = {

            if (checkImageInList.value) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listPoster
                        .take(4)
                        .forEach {
                            item {
                                ComponentMovieDetail.ImageCardMovie(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    previewUrl = it.previewUrl
                                )
                                if (listPoster[3].previewUrl == it.previewUrl) {
                                    GetMoreInformationMovie(
                                        onClickGetMoreInformation = loadAllImageMovieClick
                                    )
                                }
                            }
                        }
                }
            }
        }
    )
    Spacer(modifier = Modifier.height(16.dp))
    if (actors.isNotEmpty()) {
        Text(
            text = "Актёры",
            color = Color.Black,
            fontSize = 18.sp
        )
        ActorsComponent(actors = actors)
    }
    if (rentalMovie.isNotEmpty()) {
        Spacer(modifier = Modifier.height(height = 15.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Премьеры",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(height = 15.dp))
        RentalMovieComponent(listRental = rentalMovie)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = getAwardsByMovie,
            modifier = Modifier.width(width = 250.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Text(
                text = "Посмотреть награды",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
    Spacer(modifier = Modifier.height(height = 15.dp))
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Рецензии",
        color = Color.Black,
        textAlign = TextAlign.Center,
        fontSize = 18.sp
    )
    Spacer(modifier = Modifier.height(height = 15.dp))
    if (viewModel.checkLoadReviewComponent.value) {
        ReviewsComponent(
            reviews = reviewList,
            getAllReviewsClick = getAllReviewsClick
        )
    }
    if (viewModel.checkLoadSimilarList.value) {
        Spacer(modifier = Modifier.height(height = 15.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Похожие фильмы",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(height = 15.dp))
        SimilarMovieComponent(
            similarMovie = similar,
            getMovieById = getNewMovieById
        )
    }
    if (viewModel.checkLoadSequelAndPrequelList.value) {
        Spacer(modifier = Modifier.height(height = 15.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Сиквелы и приквелы",
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(height = 15.dp))
        SequelsAndPrequelsMovieComponent(
            sequelAndPrequel = sequelAndPrequel,
            getNewMovieById = getNewMovieById
        )
    }
}

@Composable
private fun SequelsAndPrequelsMovieComponent(
    modifier: Modifier = Modifier,
    sequelAndPrequel: List<MoviesSequelAndPrequel>,
    getNewMovieById: (Int) -> Unit
) {

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        sequelAndPrequel.forEach {
            item {
                Spacer(modifier = Modifier.width(width = 8.dp))
                MovieCardComponent(
                    modifier = Modifier.clickable { getNewMovieById(it.filmId) },
                    image = it.posterUrlPreview,
                    nameRu = it.nameRu,
                    nameEn = it.nameEn
                )
            }
        }
    }

}

@Composable
private fun SimilarMovieComponent(
    modifier: Modifier = Modifier,
    similarMovie: List<SimilarMovie>,
    getMovieById: (id: Int) -> Unit
) {

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 220.dp),
    ) {
        similarMovie.forEach { similarMovie ->
            item {
                Spacer(modifier = Modifier.width(width = 8.dp))
                similarMovie.nameEn?.let {
                    MovieCardComponent(
                        modifier = Modifier.clickable { getMovieById(similarMovie.filmId) },
                        image = similarMovie.posterUrlPreview,
                        nameRu = similarMovie.nameRu,
                        nameEn = similarMovie.nameEn
                    )
                }

            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MovieCardComponent(
    modifier: Modifier = Modifier,
    image: String,
    nameRu: String,
    nameEn: String
) {
    Card(
        modifier = modifier
            .width(140.dp)
            .fillMaxHeight(),
        backgroundColor = Color.Black
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                GlideImage(
                    modifier = Modifier
                        .height(100.dp),
                    model = image,
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = nameRu,
                fontSize = 16.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = nameEn,
                fontSize = 13.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun ReviewsComponent(
    modifier: Modifier = Modifier,
    getAllReviewsClick: () -> Unit,
    reviews: List<Review>
) {

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 150.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        reviews
            .take(5)
            .forEach {
                item {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .size(width = 250.dp, height = 125.dp),
                        backgroundColor = Color.Black
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.padding(top = 8.dp)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .weight(1f),
                                    text = "Автор: ${it.author}",
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                                GetEmojiByReviewType(
                                    modifier = Modifier.padding(end = 16.dp),
                                    reviewType = it.type
                                )
                            }
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = "Дата: ${
                                    it.date.removeRange(
                                        startIndex = 9,
                                        endIndex = 18
                                    )
                                }",
                                color = Color.White,
                                fontSize = 14.sp
                            )
                            Text(
                                text = it.description,
                                maxLines = 5,
                                color = Color.White,
                                overflow = TextOverflow.Ellipsis,
                                fontSize = 14.sp
                            )
                        }
                    }
                    if (reviews[4].author == it.author) {
                        GetMoreInformationMovie(onClickGetMoreInformation = getAllReviewsClick)
                    }
                }
            }
    }

}

@Composable
fun GetEmojiByReviewType(
    modifier: Modifier = Modifier,
    reviewType: ReviewType
) {
    when (reviewType) {
        ReviewType.POSITIVE -> {
            Icon(
                modifier = modifier.size(size = 24.dp),
                imageVector = MyIcons.PositiveEmoji,
                contentDescription = "",
                tint = Color.Green
            )
        }

        ReviewType.NEGATIVE -> {
            Icon(
                modifier = modifier.size(size = 24.dp),
                imageVector = MyIcons.NegativeEmoji,
                contentDescription = "",
                tint = Color.Red
            )
        }

        ReviewType.NEUTRAL -> {
            Icon(
                modifier = modifier.size(size = 24.dp),
                imageVector = MyIcons.NeutralEmoji,
                contentDescription = "",
                tint = Color.Gray
            )
        }

        ReviewType.NOT_SELECTED -> TODO()
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ActorsComponent(
    modifier: Modifier = Modifier,
    actors: List<Actor>
) {
    Spacer(modifier = Modifier.height(16.dp))

    LazyHorizontalGrid(
        rows = GridCells.Fixed(count = 3),
        modifier = modifier
            .height(height = 250.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        actors.forEach {
            item {
                Card(
                    modifier = Modifier.size(width = 300.dp, height = 64.dp),
                    backgroundColor = Color.Black
                ) {
                    Row {
                        GlideImage(
                            modifier = Modifier.fillMaxHeight(),
                            model = it.posterUrl,
                            contentDescription = ""
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp)
                        ) {
                            Text(
                                text = it.nameRu,
                                color = Color.White
                            )
                            Text(
                                text = it.nameEn,
                                color = Color.White
                            )
                            Text(
                                text = it.professionText,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun RentalMovieComponent(
    modifier: Modifier = Modifier,
    listRental: Set<RentalMovie>
) {

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        listRental.forEach {
            item {
                Card(
                    modifier = Modifier
                        .width(width = 200.dp)
                        .padding(end = 15.dp),
                    backgroundColor = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Column {
                        it.country?.let {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = it.country,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center
                            )
                        }
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Премьера: ${it.date}",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

}

@Composable
private fun GetMoreInformationMovie(
    modifier: Modifier = Modifier,
    onClickGetMoreInformation: () -> Unit
) {
    IconButton(
        modifier = modifier.padding(all = 16.dp),
        onClick = onClickGetMoreInformation
    ) {
        Icon(
            modifier = Modifier.size(45.dp),
            tint = Color.Black,
            imageVector = MyIcons.ArrowForward,
            contentDescription = ""
        )
    }
}

@Composable
private fun OpenDescriptionMovie(
    movieDetail: MovieDetail,
    maxLines: Int,
    isClosedDescription: () -> Unit,
    isOpenDescription: () -> Unit
) {
    Text(
        text = movieDetail.description,
        color = Color.Black,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
    )
    if (maxLines == 3) {
        TextButton(
            onClick = isOpenDescription
        ) {
            Text(
                text = "Показать описание полностью",
                color = Color.Red
            )
        }
    }
    if (maxLines == Int.MAX_VALUE) {
        TextButton(
            onClick = isClosedDescription
        ) {
            Text(
                text = "Скрыть описание",
                color = Color.Red
            )
        }
    }
}

@Composable
private fun Rating(
    movieDetail: MovieDetail
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Card(
            modifier = Modifier
                .height(64.dp)
                .width(150.dp),
            backgroundColor = MaterialTheme.colorScheme.secondary
        ) {
            Box(
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = movieDetail.ratingImdb.toString(),
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    modifier = Modifier.padding(top = 25.dp),
                    text = "Рейтинг IMDB",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp
                )
            }
        }
        Card(
            modifier = Modifier
                .height(64.dp)
                .width(150.dp),
            backgroundColor = MaterialTheme.colorScheme.secondary
        ) {
            Box(
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = movieDetail.ratingKinopoisk.toString(),
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Text(
                    modifier = Modifier.padding(top = 25.dp),
                    text = "Рейтинг Кинопоиска",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun CardFunctionMovieComponent(
    modifier: Modifier = Modifier,
    onClickSaveToFavoriteMovie: () -> Unit,
    onClickRemoveMovieFromFavorite: () -> Unit,
    checkMovieInFavorite: Boolean
) {

    val stateButtons = remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(
                onClick = {
                    if (!checkMovieInFavorite) {
                        onClickSaveToFavoriteMovie()
                    }
                    if (checkMovieInFavorite) {
                        onClickRemoveMovieFromFavorite()
                    }
                }
            ) {
                if (checkMovieInFavorite) {
                    Icon(
                        imageVector = MyIcons.Heart_minus,
                        contentDescription = "",
                        tint = Color.Black
                    )
                    Text(
                        modifier = Modifier.padding(top = 45.dp),
                        text = "Удалить из избранных",
                        color = Color.Black
                    )
                }
                if (!checkMovieInFavorite) {
                    Icon(
                        imageVector = MyIcons.Heart_plus,
                        contentDescription = "",
                        tint = Color.Black
                    )
                    Text(
                        modifier = Modifier.padding(top = 45.dp),
                        text = "Сохранить",
                        color = Color.Black
                    )
                }
            }
            IconButton(
                onClick = {
                    showDialog.value = true
                },
                enabled = true
            ) {
                Icon(
                    imageVector = MyIcons.Star,
                    contentDescription = "",
                    tint = Color.Black
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
                Text(
                    modifier = Modifier.padding(top = 45.dp),
                    text = "Оценить",
                    color = Color.Black
                )
            }
        }
    }
}