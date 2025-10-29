package com.example.flavi.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.flavi.model.data.repository.UserRepositoryImpl
import com.google.firebase.auth.userProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UpdateAccountViewModel @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl
): ViewModel() {

    fun getCurrentUser() = userRepositoryImpl.getCurrentUser()!!

    fun updateProfile(name: String) {
        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }

        getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Auth", "Профиль обновлён")
            }
        }
    }

}
