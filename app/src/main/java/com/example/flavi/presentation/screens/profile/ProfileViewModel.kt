package com.example.flavi.presentation.screens.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class ProfileViewModel: ViewModel() {

    val stateProfile = mutableStateOf("")

    init {
        val user = Firebase.auth.currentUser
        user?.let {
            stateProfile.value = it.email ?: "Not found"
        }
    }
}