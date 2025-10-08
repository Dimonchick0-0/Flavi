package com.example.flavi.viewmodel

import androidx.compose.ui.util.fastAny
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.model.data.repository.UserRepositoryImpl
import com.example.flavi.model.domain.entity.kinopoiskUnOfficial.Poster
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PosterScreenViewModel @Inject constructor(
    private val repositoryImpl: UserRepositoryImpl
): ViewModel() {

    suspend fun getAllImage(id: Int, type: String): List<Poster> {
        return repositoryImpl.loadAllImageMovie(id, type)
    }

}