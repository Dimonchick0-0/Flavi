package com.example.flavi.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.model.data.repository.UserRepositoryImpl
import com.example.flavi.model.domain.usecase.AuthUserUseCase
import com.example.flavi.view.state.AuthUserState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthUserViewModel @Inject constructor(
    private val repository: UserRepositoryImpl,
    private val authUserUseCase: AuthUserUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<AuthUserState> = MutableStateFlow(AuthUserState.AuthUser())
    val state
        get() = _state.asStateFlow()

    val errorStateEmail = mutableStateOf(false)
    val errorStatePassword = mutableStateOf(false)

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
                    if (!checkUserByEmailAndPassword(email, password)) {
                        Log.d("Auth", "Not found user")
                        AuthUserState.AuthUser()
                    } else {
                        signInUser(password, email)
                        AuthUserState.Finished
                    }
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

    private suspend fun checkUserByEmailAndPassword(email: String, password: String): Boolean {
        return repository.checkUser(email, password)
    }

}