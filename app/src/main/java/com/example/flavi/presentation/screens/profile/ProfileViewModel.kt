package com.example.flavi.presentation.screens.profile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    val auth = Firebase.auth

    private val stateName = mutableStateOf("")
    private val stateEmail = mutableStateOf("")

    private val _stateProfile: MutableStateFlow<ProfileState> = MutableStateFlow(
        ProfileState.Initial(
            nameUser = "",
            emailUser = ""
        )
    )
    val stateProfile: StateFlow<ProfileState> = _stateProfile.asStateFlow()

    private fun getCurrentName() {
        auth.addAuthStateListener {
            stateName.value = it.currentUser?.displayName.toString()
        }
    }

    private fun getCurrentEmailUser() {
        auth.addAuthStateListener {
            stateEmail.value = it.currentUser?.email.toString()
        }
    }

    fun initialUser() {
        _stateProfile.update { state ->
            if (state is ProfileState.Initial) {
                getCurrentName()
                getCurrentEmailUser()
                state.copy(
                    nameUser = stateName.value,
                    emailUser = stateEmail.value
                )
            } else {
                state
            }
        }
    }

    fun logOutOfYourAccount() { Firebase.auth.signOut() }
}

sealed interface ProfileState {
    data class Initial(
        val nameUser: String,
        val emailUser: String
    ) : ProfileState
}