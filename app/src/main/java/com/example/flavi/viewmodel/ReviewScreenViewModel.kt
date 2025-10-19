package com.example.flavi.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.model.data.database.map.toReview
import com.example.flavi.model.data.database.map.toReviewList
import com.example.flavi.model.data.datasource.reviews.ReviewListDTO
import com.example.flavi.model.data.repository.UserRepositoryImpl
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Review
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.ReviewList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewScreenViewModel @Inject constructor(
    private val repositoryImpl: UserRepositoryImpl
) : ViewModel() {

    private val _state: MutableStateFlow<ReviewsScreenState> = MutableStateFlow(
        value = ReviewsScreenState(
            reviews = ReviewList()
        )
    )

    val state = _state.asStateFlow()

    fun getReviews(filmId: Int) {
        viewModelScope.launch {
            repositoryImpl.getReviewsListByMovieId(id = filmId).body()?.let {
                _state.emit(value = ReviewsScreenState(reviews = it.toReviewList()))
            }
        }
    }

}

data class ReviewsScreenState(val reviews: ReviewList)