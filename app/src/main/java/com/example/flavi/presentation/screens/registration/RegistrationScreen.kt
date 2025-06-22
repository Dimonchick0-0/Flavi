package com.example.flavi.presentation.screens.registration

import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flavi.presentation.screens.components.RegistrationButton
import com.example.flavi.presentation.screens.components.RegistrationTextField
import com.example.flavi.presentation.state.RegisterState

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current.applicationContext,
    viewModel: RegisterViewModel = viewModel {
        RegisterViewModel(context)
    },
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
                Row(modifier = Modifier.padding(top = 40.dp)) {
                    RegistrationTextField(
                        text = "Имя",
                        value = name,
                        onQueryChange = {
                            viewModel.processCommand(RegisterCommand.InputName(it))
                        }
                    )
                    RegistrationTextField(
                        text = "Почта",
                        value = email,
                        onQueryChange = { viewModel.processCommand(RegisterCommand.InputEmail(it)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                }
                Row {
                    RegistrationTextField(
                        text = "Пароль",
                        value = password,
                        onQueryChange = { viewModel.processCommand(RegisterCommand.InputPassword(it)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                    )
                    RegistrationTextField(
                        text = "Повторите пароль",
                        value = confirmPassword,
                        onQueryChange = {
                            viewModel.processCommand(RegisterCommand.InputConfirmPassword(it))
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
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
                                    if (password.length != 6 && confirmPassword.length != 6) {
                                        Log.d("RegistrationScreen", "пароль должен быть больше 6 символов")
                                    } else {
                                        viewModel.processCommand(RegisterCommand.RegisterUser)
                                    }
                                }
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