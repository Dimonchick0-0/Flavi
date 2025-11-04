package com.example.flavi.view.screens.person

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.MoviePerson
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Person
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.ProfessionKey
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.SEX
import com.example.flavi.viewmodel.PersonsScreenViewModel

@Composable
fun PersonsScreen(
    modifier: Modifier = Modifier,
    viewModel: PersonsScreenViewModel = hiltViewModel(),
    personId: Int
) {

    val state = viewModel.state.collectAsStateWithLifecycle()

    viewModel.getPersonDetail(id = personId)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        viewModel.processPersonScreenState()
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                ActorDetail(
                    person = state.value.person,
                    viewModel = viewModel
                )
            }
        }
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ActorDetail(
    modifier: Modifier = Modifier,
    viewModel: PersonsScreenViewModel,
    person: Person
) {
    Log.d("Auth", person.personId.toString())
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space = 28.dp)
    ) {
        GlideImage(
            modifier = Modifier
                .size(size = 200.dp)
                .padding(start = 8.dp),
            model = person.posterUrl,
            contentDescription = ""
        )
        Column {
            Text(
                text = person.nameRu,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(height = 16.dp))
            Text(
                text = person.nameEn,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(height = 16.dp))
            Text(
                text = "Дата рождения: ${person.birthday}",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp
            )
            if (person.death != null) {
                Spacer(modifier = Modifier.height(height = 16.dp))
                Text(
                    text = "Дата смерти: ${person.death}",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(height = 16.dp))
            Text(
                text = "Пол: ${getSexPerson(person.sex)}",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(height = 16.dp))
            Text(
                text = person.profession,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp
            )
        }
    }
    Spacer(modifier = Modifier.height(height = 16.dp))
    Column {
        Text(
            text = "Фильмы и сериалы",
            color = MaterialTheme.colorScheme.primary,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(height = 16.dp))
        LazyRow {
            person.films
                .distinctBy { it.professionKey }
                .forEach { moviePerson ->
                    item {
                        FilterPersonMovie(
                            viewModel = viewModel,
                            moviePerson = moviePerson
                        )
                    }
                }

        }
        Spacer(modifier = Modifier.height(height = 16.dp))
        LazyRow {
            person.films
                .filter { it.professionKey == viewModel.stateProfessionKeuPerson.value }
                .forEach { movies ->
                    if (movies.nameRu != null && movies.rating != null) {
                        item {
                            Card {
                                Text(
                                    text = movies.nameRu,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
        }
    }
}

@Composable
private fun FilterPersonMovie(
    modifier: Modifier = Modifier,
    viewModel: PersonsScreenViewModel,
    moviePerson: MoviePerson
) {

    val colorIsFocused = remember { mutableStateOf(Color.White) }
    val isFocused = remember { mutableStateOf(false) }
    val requester = remember { FocusRequester() }

    val professionKeyPerson = listOf(moviePerson.professionKey)
    professionKeyPerson.forEach {
        Card(
            modifier = modifier
                .size(width = 140.dp, height = 40.dp)
                .clickable {
                    requester.requestFocus(focusDirection = FocusDirection.Enter)
                    viewModel.stateProfessionKeuPerson.value = it
                }
                .focusRequester(requester)
                .onFocusChanged { focused ->
                    colorIsFocused.value = if (focused.isFocused) Color.Black else Color.White
                    isFocused.value = focused.isFocused
                }
                .focusable(),
            colors = CardDefaults.cardColors(
                containerColor = if (isFocused.value) Color.White else MaterialTheme.colorScheme.tertiary
            )
        ) {
            val newProfessionKeyText = when (it) {
                ProfessionKey.ACTOR -> "Актёр"
                ProfessionKey.WRITER -> "Режиссёр"
                ProfessionKey.HIMSELF -> "Играл себя"
                ProfessionKey.PRODUCER -> "Продюсер"
                ProfessionKey.DIRECTOR -> "Директор"
                ProfessionKey.HRONO_TITR_MALE -> "В титрах не указан"
                ProfessionKey.HRONO_TITR_FEMALE -> "В титрах не указан"
                ProfessionKey.HERSELF -> "Играл себя"
                ProfessionKey.NOT_SELECTED -> ""
            }

            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                text = newProfessionKeyText,
                color = if (isFocused.value) Color.Black else Color.White
            )
        }
    }

}

private fun getSexPerson(sex: SEX): String {
    return when (sex) {
        SEX.MALE -> "Мужской"
        SEX.FEMALE -> "Женский"
    }
}
