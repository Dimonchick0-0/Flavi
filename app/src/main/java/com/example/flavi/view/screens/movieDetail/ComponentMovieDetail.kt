package com.example.flavi.view.screens.movieDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

object ComponentMovieDetail {

    @OptIn(ExperimentalGlideComposeApi::class)
    @Composable
    fun ImageCardMovie(
        modifier: Modifier = Modifier,
        previewUrl: String
    ) {
        GlideImage(
            modifier = modifier.size(160.dp),
            model = previewUrl,
            contentDescription = ""
        )
    }

    @Composable
    fun CardInFilterQueryImage(
        modifier: Modifier = Modifier,
        listCardFilterImageQuery: List<String>,
        loadImageClick: (String) -> Unit,
        content: @Composable () -> Unit
    ) {
        val selectedNumber = remember { mutableIntStateOf(-1) }
        val typeImage = remember { mutableStateOf("") }

        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            backgroundColor = MaterialTheme.colorScheme.tertiary
        ) {
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                listCardFilterImageQuery.forEachIndexed { index, s ->
                    val colorItemSelected =
                        if (index == selectedNumber.intValue)
                            Color.Green
                        else
                            MaterialTheme.colorScheme.primary
                    TextButton(
                        onClick = {
                            selectedNumber.intValue = index
                            when (s) {
                                "Кадры" -> typeImage.value = "STILL"
                                "Постеры" -> typeImage.value = "POSTER"
                                "Фан-арты" -> typeImage.value = "FAN_ART"
                            }
                            loadImageClick(typeImage.value)
                        },
                    ) {
                        Text(
                            text = s,
                            color = colorItemSelected
                        )
                    }
                }
            }
        }
        Surface(
            color = Color.White,
            content = content
        )

    }

}