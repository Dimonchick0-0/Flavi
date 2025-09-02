package com.example.flavi.view.screens.movieDetail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MovieDetail
import com.example.flavi.view.screens.searchMovie.AlertDialogEstimateMovie
import com.example.flavi.view.ui.theme.MyIcons
import com.example.flavi.viewmodel.MovieDetailState
import com.example.flavi.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun MovieDetail(
    modifier: Modifier = Modifier,
    filmId: Int,
    viewModel: MovieDetailViewModel = hiltViewModel()
) {

    val state = viewModel.movieDetailState.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onBackground)
        ) {
            when (val currentState = state.value) {

                is MovieDetailState.LoadMovieDetail -> {
                    viewModel.getMovieById(filmId)

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
                                    checkMovieInFavorite.value = true
                                }
                            }
                        },
                        onClickSaveToFavoriteMovie = {
                            coroutineScope.launch {
                                viewModel.apply {
                                    saveMovieToFavorite(currentState.movie)
                                    checkMovieInFavorite.value = false
                                }
                            }
                        },
                        checkMovieInFavorite = viewModel.checkMovieInFavorite.value
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MovieDetailComponent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail,
    onClickSaveToFavoriteMovie: () -> Unit,
    onClickRemoveMovieFromFavorite: () -> Unit,
    checkMovieInFavorite: Boolean
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            modifier = modifier.height(300.dp),
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
        movieDetail.countries.forEach {
            Text(
                text = it.country,
                color = Color.Black
            )
        }
        CardFunctionMovieComponent(
            onClickRemoveMovieFromFavorite = onClickRemoveMovieFromFavorite,
            onClickSaveToFavoriteMovie = onClickSaveToFavoriteMovie,
            checkMovieInFavorite = checkMovieInFavorite
        )
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
        Row {
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
                        imageVector = Icons.Default.Favorite,
                        contentDescription = ""
                    )
                }
                if (!checkMovieInFavorite) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = ""
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
    }
}