package com.example.flavi.presentation.screens.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {


    fun getCurrentNameUser(): String {
        val currentUser = Firebase.auth.currentUser
        return currentUser?.displayName!!
    }

    fun getCurrentEmailUser(): String {
        val currentUser = Firebase.auth.currentUser
        return currentUser?.email!!
    }

    fun logOutOfYourAccount() {
        Firebase.auth.signOut()
    }
}