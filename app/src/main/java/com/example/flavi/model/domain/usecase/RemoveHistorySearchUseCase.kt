package com.example.flavi.model.domain.usecase

import com.example.flavi.model.domain.repository.UserRepository
import javax.inject.Inject

class RemoveHistorySearchUseCase @Inject constructor(
    private val repository: UserRepository
) {

    suspend operator fun invoke(title: String) {
        repository.removeHistorySearch(title)
    }

}