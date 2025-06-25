package com.example.flavi.presentation.screens.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flavi.presentation.screens.components.RegistrationButton
import com.example.flavi.presentation.screens.components.RegistrationTextField
import com.example.flavi.presentation.state.RegisterState

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
    onRegisterClick: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val currentState = state.value
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    shape = RoundedCornerShape(
                        bottomStart = 35.dp,
                        bottomEnd = 35.dp
                    )
                )
                .background(color = MaterialTheme.colorScheme.background)
                .height(400.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = "Вас приветствует Flavi",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 34.sp
                )
                Text(
                    modifier = Modifier.padding(top = 30.dp),
                    text = "Регистрация аккаунта",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp
                )
            }
        }
        when (currentState) {
            RegisterState.FinishedRegister -> {
                onRegisterClick()
            }
            is RegisterState.Input -> {
                val name = currentState.name
                val email = currentState.email
                val password = currentState.password
                val confirmPassword = currentState.confirmPassword
                val color = if (password.length < 6 && confirmPassword.length < 6) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
                Row(modifier = Modifier.padding(top = 40.dp)) {
                    RegistrationTextField(
                        text = "Имя",
                        value = name,
                        onQueryChange = {
                            viewModel.processCommand(RegisterCommand.InputName(it))
                            viewModel.errorStateName.value = false
                        },
                        isError = viewModel.errorStateName.value,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.surface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                            errorBorderColor = color
                        )
                    )
                    RegistrationTextField(
                        text = "Почта",
                        value = email,
                        onQueryChange = {
                            viewModel.processCommand(RegisterCommand.InputEmail(it))
                            viewModel.errorStateEmail.value = false
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        isError = viewModel.errorStateEmail.value,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.surface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                            errorBorderColor = color
                        )
                    )
                }
                Row {
                    RegistrationTextField(
                        text = "Пароль",
                        value = password,
                        onQueryChange = {
                            viewModel.errorStatePassword.value = it.length <= 5
                            viewModel.processCommand(RegisterCommand.InputPassword(it))
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation('*'),
                        isError = viewModel.errorStatePassword.value,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.surface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                            errorBorderColor = color
                        )
                    )
                    RegistrationTextField(
                        text = "Повторите пароль",
                        value = confirmPassword,
                        onQueryChange = {
                            viewModel.errorStateConfirmPassword.value = it.length <= 5
                            viewModel.processCommand(RegisterCommand.InputConfirmPassword(it))
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = PasswordVisualTransformation('*'),
                        isError = viewModel.errorStateConfirmPassword.value,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.surface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                            errorBorderColor = color
                        )
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    RegistrationButton(
                        buttonOnClick = {
                            if (name.isNotBlank()
                                && email.isNotBlank()
                                && password.isNotBlank()
                                && confirmPassword.isNotBlank()
                            ) {
                                if (passwordVerification(password = password, confirmPassword = confirmPassword)) {
                                    viewModel.processCommand(RegisterCommand.RegisterUser)
                                }
                            }
                            name.ifEmpty {
                                viewModel.errorStateName.value = true
                                color.value
                            }
                            email.ifEmpty {
                                viewModel.errorStateEmail.value = true
                                color.value
                            }
                            password.ifEmpty {
                                viewModel.errorStatePassword.value = true
                                color.value
                            }
                            confirmPassword.ifEmpty {
                                viewModel.errorStateConfirmPassword.value = true
                                color.value
                            }
                        }
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.surface)) {
                                append("Уже есть аккаунт?")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.surface,
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append(" Авторизуйтесь!")
                            }
                        }
                    )
                }
            }
        }
    }
}

private fun passwordVerification(password: String, confirmPassword: String): Boolean {
    return password == confirmPassword
}