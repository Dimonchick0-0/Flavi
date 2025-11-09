package com.example.flavi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.flavi.model.data.repository.UserRepositoryImpl
import com.google.firebase.auth.userProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateAccountViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl
): ViewModel() {

    var showIndicatorIfProfileIsUpdated by mutableStateOf(false)
    var showDialogIfProfileIsUpdated by mutableStateOf(false)

    fun getCurrentUser() = userRepositoryImpl.getCurrentUser()!!

    fun updateProfile(name: String) {
        val profileUpdates = userProfileChangeRequest {
            displayName = name
            showIndicatorIfProfileIsUpdated = true
        }

        getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showIndicatorIfProfileIsUpdated = false
                showDialogIfProfileIsUpdated = true
            }
        }
    }

}
