package com.example.flavi.view.screens.movieDetail.review

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Review
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.ReviewType
import com.example.flavi.view.screens.movieDetail.GetEmojiByReviewType
import com.example.flavi.view.ui.theme.MyIcons
import com.example.flavi.viewmodel.ReviewScreenViewModel

@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier,
    filmId: Int,
    viewModel: ReviewScreenViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsStateWithLifecycle()

    viewModel.getReviews(filmId)

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->

        val reviewsType = remember { mutableStateOf(ReviewType.NOT_SELECTED) }

        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.onBackground)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .size(58.dp),
                    backgroundColor = Color.White,
                    elevation = 10.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Всего: ${state.value.reviews.total}",
                            color = Color.Black
                        )
                        CardReviewsType(
                            image = MyIcons.PositiveEmoji,
                            color = Color.Green,
                            text = state.value.reviews.totalPositiveReviews.toString(),
                            getReviewTypeClick = {
                                reviewsType.value = ReviewType.POSITIVE
                            }
                        )
                        CardReviewsType(
                            image = MyIcons.NegativeEmoji,
                            color = Color.Red,
                            text = state.value.reviews.totalNegativeReviews.toString(),
                            getReviewTypeClick = {
                                reviewsType.value = ReviewType.NEGATIVE
                            }
                        )
                        CardReviewsType(
                            image = MyIcons.NeutralEmoji,
                            color = Color.Gray,
                            text = state.value.reviews.totalNeutralReviews.toString(),
                            getReviewTypeClick = {
                                reviewsType.value = ReviewType.NEUTRAL
                            }
                        )
                    }
                }
            }
            item {
                ReviewsComponent(
                    reviewsType = reviewsType.value,
                    reviews = state.value.reviews.items
                )
            }
        }
    }

}

@Composable
private fun ReviewsComponent(
    modifier: Modifier = Modifier,
    reviewsType: ReviewType,
    reviews: List<Review>
) {
    reviews
        .filter { it.type == reviewsType }
        .forEach {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Column(
                    modifier = Modifier.padding(start = 8.dp, top = 8 .dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Автор: ${it.author}",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        GetEmojiByReviewType(
                            modifier = Modifier.padding(top = 8.dp, end = 8.dp),
                            reviewType = it.type
                        )
                    }
                    Text(
                        text = "Дата: ${it.date.removeRange(startIndex = 9, endIndex = 18)}",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(height = 8.dp))
                    Text(
                        text = "-------------------------------------------------",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = it.description,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
}

@Composable
private fun CardReviewsType(
    modifier: Modifier = Modifier,
    image: ImageVector,
    color: Color,
    text: String,
    getReviewTypeClick: () -> Unit
) {

    val colorIsFocused = remember { mutableStateOf(Color.White) }
    val isFocused = remember { mutableStateOf(false) }
    val requester = remember { FocusRequester() }

    Box(
        modifier = modifier
            .clickable {
                requester.requestFocus(focusDirection = FocusDirection.Enter)
                getReviewTypeClick()
            }
            .focusRequester(requester)
            .onFocusChanged {
                colorIsFocused.value = if (it.isFocused) Color.Black else Color.White
                isFocused.value = it.isFocused
            }
            .focusable()
            .background(color = colorIsFocused.value)
    ) {
        Row {

            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = image,
                contentDescription = "",
                tint = color
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = if (isFocused.value) Color.White else Color.Black
            )

        }
    }

}