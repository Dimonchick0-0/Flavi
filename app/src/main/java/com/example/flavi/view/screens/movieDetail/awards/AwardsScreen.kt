package com.example.flavi.view.screens.movieDetail.awards

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavi.viewmodel.AwardsScreenViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Awards
import com.example.flavi.viewmodel.AwardsScreenState

@Composable
fun AwardsScreen(
    modifier: Modifier = Modifier,
    viewModel: AwardsScreenViewModel = hiltViewModel(),
    filmId: Int
) {

    val state = viewModel.state.collectAsStateWithLifecycle()

    viewModel.getAwards(filmId)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.onBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                when (val currentState = state.value) {
                    is AwardsScreenState.AwardsScreen -> {
                        AwardsComponent(awards = currentState.awards)
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun AwardsComponent(
    modifier: Modifier = Modifier,
    awards: List<Awards>
) {

    if (awards.isNotEmpty()) {
        awards.forEach { award ->
            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = "Премия: ${award.name}",
                color = Color.Black,
                fontSize = 21.sp
            )
            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = "Название номинации: ${award.nominationName}",
                color = Color.Black,
                fontSize = 21.sp
            )
            Text(
                modifier = Modifier.padding(all = 8.dp),
                text = "Персоны",
                color = Color.Black,
                fontSize = 21.sp
            )
            LazyRow {
                award.persons.forEach { persons ->
                    item {
                        Card(
                            modifier = modifier
                                .size(width = 250.dp, height = 80.dp)
                                .padding(end = 8.dp),
                            backgroundColor = Color.Black
                        ) {
                            Row {
                                persons?.let {
                                    GlideImage(
                                        modifier = Modifier.fillMaxHeight(),
                                        model = it.posterUrl,
                                        contentDescription = ""
                                    )
                                    Column {
                                        Text(
                                            text = persons.nameRu,
                                            color = Color.White
                                        )
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = persons.nameEn,
                                            color = Color.White
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
