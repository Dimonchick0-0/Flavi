package com.example.flavi.presentation.screens.registration

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavi.domain.usecase.RegistrationUserUseCase
import com.example.flavi.presentation.state.RegisterState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor (
    private val registrationUserUseCase: RegistrationUserUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState.Input())
    val state: StateFlow<RegisterState>
        get() = _state.asStateFlow()

    val errorStateName = mutableStateOf(false)
    val errorStateEmail = mutableStateOf(false)
    val errorStatePassword = mutableStateOf(false)
    val errorStateConfirmPassword = mutableStateOf(false)

    fun processCommand(registerCommand: RegisterCommand) {
        when (registerCommand) {
            is RegisterCommand.InputConfirmPassword -> {
                _state.update { previousState ->
                    if (previousState is RegisterState.Input) {
                        previousState.copy(confirmPassword = registerCommand.confirmPassword)
                    } else {
                        RegisterState.Input(confirmPassword = registerCommand.confirmPassword)
                    }
                }
            }

            is RegisterCommand.InputEmail -> {
                _state.update { previousState ->
                    if (previousState is RegisterState.Input) {
                        previousState.copy(email = registerCommand.email)
                    } else {
                        RegisterState.Input(email = registerCommand.email)
                    }
                }
            }

            is RegisterCommand.InputName -> {
                _state.update { previousState ->
                    if (previousState is RegisterState.Input) {
                        previousState.copy(name = registerCommand.name)
                    } else {
                        RegisterState.Input(name = registerCommand.name)
                    }
                }
            }

            is RegisterCommand.InputPassword -> {
                _state.update { previousState ->
                    if (previousState is RegisterState.Input) {
                        previousState.copy(password = registerCommand.password)
                    } else {
                        RegisterState.Input(password = registerCommand.password)
                    }
                }
            }

            RegisterCommand.RegisterUser -> {
                viewModelScope.launch {
                    _state.update { previousState ->
                        if (previousState is RegisterState.Input) {
                            val name = previousState.name.trim()
                            val email = previousState.email.trim()
                            val password = previousState.password.trim()
                            registerUser(
                                name = name,
                                password = password,
                                email = email
                            )
                            RegisterState.FinishedRegister
                        } else {
                            previousState
                        }
                    }
                }
            }
        }
    }

    private fun registerUser(name: String, password: String, email: String) {
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("RegistrationFirebase", "createUserWithEmail:success")
                    viewModelScope.launch {
                        registrationUserUseCase(
                            name = name,
                            password = password,
                            email = email
                        )
                        val currentUser = auth.currentUser
                        val updateProfile = userProfileChangeRequest {
                            displayName = name
                        }
                        currentUser?.updateProfile(updateProfile)?.addOnCompleteListener {
                            if (it.isSuccessful) {
                                Log.d("Auth", currentUser.displayName.toString())
                            }
                        }
                    }
                } else {
                    Log.d("RegistrationFirebase", "createUserWithEmail:success", task.exception)
                }
            }

    }
}

sealed interface RegisterCommand {
    data class InputName(val name: String) : RegisterCommand
    data class InputEmail(val email: String) : RegisterCommand
    data class InputPassword(val password: String) : RegisterCommand
    data class InputConfirmPassword(val confirmPassword: String) : RegisterCommand

    data object RegisterUser : RegisterCommand
}