package com.example.flavi.view.screens.movieDetail.poster

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Poster
import com.example.flavi.view.screens.movieDetail.ComponentMovieDetail
import com.example.flavi.viewmodel.PosterScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PosterScreen(
    modifier: Modifier = Modifier,
    viewModel: PosterScreenViewModel = hiltViewModel(),
    filmId: Int
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.onBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

            val coroutineScope = rememberCoroutineScope()
            val listTypeImage = listOf("Кадры", "Постеры", "Фан-арты")
            val listImageMovie = mutableListOf<Poster>()
            val checkImageMovie = remember { mutableStateOf(false) }

            ComponentMovieDetail.CardInFilterQueryImage(
                listCardFilterImageQuery = listTypeImage,
                loadImageClick = { s ->
                    coroutineScope.launch {
                        viewModel.apply {
                            if (listImageMovie.isNotEmpty()) {
                                listImageMovie.clear()
                                checkImageMovie.value = false
                            }

                            if (listImageMovie.isEmpty()) {
                                getAllImage(id = filmId, type = s).forEach { poster ->
                                    listImageMovie.add(poster)
                                    checkImageMovie.value = true
                                }
                            }

                        }

                    }
                },
                content = {

                    if (checkImageMovie.value) {
                        LazyVerticalStaggeredGrid(
                            modifier = Modifier.fillMaxSize(),
                            columns = StaggeredGridCells.Fixed(count = 2),
                            verticalItemSpacing = 4.dp,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            content = {
                                listImageMovie.forEach {
                                    item {
                                        GlideImage(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight(),
                                            model = it.previewUrl,
                                            contentDescription = "",
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                            }
                        )
                    }

                }
            )

        }

    }

}