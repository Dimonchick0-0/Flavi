package com.example.flavi.presentation.screens.auth

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.favre.lib.crypto.bcrypt.BCrypt
import com.example.flavi.data.repository.UserRepositoryImpl
import com.example.flavi.domain.usecase.AuthUserUseCase
import com.example.flavi.presentation.state.AuthUserState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthUserViewModel(context: Context) : ViewModel() {
    private val repository = UserRepositoryImpl.getInstance(context)

    private val authUserUseCase = AuthUserUseCase(repository)

    private val _state: MutableStateFlow<AuthUserState> = MutableStateFlow(AuthUserState.AuthUser())
    val state
        get() = _state.asStateFlow()

    fun updatePassword(password: String) {
        _state.update { authState ->
            if (authState is AuthUserState.AuthUser) {
                authState.copy(password = password)
            } else {
                authState
            }
        }
    }

    fun updateEmail(email: String) {
        _state.update { authState ->
            if (authState is AuthUserState.AuthUser) {
                Log.d("Auth", email)
                authState.copy(email = email)
            } else {
                authState
            }
        }
    }



    fun authUser() {
        viewModelScope.launch {
            _state.update { authState ->
                if (authState is AuthUserState.AuthUser) {
                    val password = authState.password
                    val email = authState.email
                    signInUser(password, email)
                    AuthUserState.Finished
                } else {
                    authState
                }
            }
        }
    }

    private fun signInUser(password: String, email: String) {
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    viewModelScope.launch {
                        authUserUseCase(password, email)
                        Log.d("Auth", "signInWithEmail:success")
                    }
                } else {
                    Log.w("Auth", "signInWithEmail:failure", task.exception)
                }
            }
    }

}