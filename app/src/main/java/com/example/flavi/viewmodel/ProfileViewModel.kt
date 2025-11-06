package com.example.flavi.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.view.state.ProfileState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    val auth = Firebase.auth

    private val stateName = mutableStateOf("")
    private val stateEmail = mutableStateOf("")

    private val _stateProfile: MutableStateFlow<ProfileState> = MutableStateFlow(
        ProfileState.Initial
    )
    val stateProfile: StateFlow<ProfileState> = _stateProfile.asStateFlow()

    fun getStateScreenIfUserRegisteredOrNotRegistered() {
        viewModelScope.launch {
            if (auth.currentUser == null) {
                _stateProfile.emit(
                    value = ProfileState.NotRegisterUser
                )
            } else {
                _stateProfile.emit(
                    value = ProfileState.RegisteredUser(
                        nameUser = "",
                        emailUser = ""
                    )
                )
            }
        }
    }

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
            if (state is ProfileState.RegisteredUser) {
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

