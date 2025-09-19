package com.example.flavi.view.screens.auth

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flavi.view.state.AuthUserState
import com.example.flavi.view.ui.theme.MyIcons
import com.example.flavi.viewmodel.AuthUserViewModel
import kotlin.time.measureTime

@Composable
fun AuthUser(
    modifier: Modifier = Modifier,
    viewModel: AuthUserViewModel = hiltViewModel(),
    onProfileClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

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
                    text = "Авторизация аккаунта",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp
                )
            }
        }

        when (val currentState = state.value) {
            is AuthUserState.AuthUser -> {
                val email = currentState.email
                val password = currentState.password
                val passwordVisibility = remember { mutableStateOf(false) }
                val color = if (password.length < 6) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
                Column(
                    modifier = Modifier.padding(top = 80.dp, start = 55.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = email,
                            onValueChange = {
                                viewModel.updateEmail(it)
                                viewModel.errorStateEmail.value = false
                            },
                            label = {
                                Text(
                                    text = "Почта",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.sp
                                )
                            },
                            isError = viewModel.errorStateEmail.value,
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                errorBorderColor = color
                            )
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            modifier = Modifier.padding(top = 35.dp),
                            value = password,
                            onValueChange = {
                                viewModel.errorStatePassword.value = it.length <= 5
                                viewModel.updatePassword(it)
                            },
                            label = {
                                Text(
                                    text = "Пароль",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.sp
                                )
                            },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            isError = viewModel.errorStatePassword.value,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Black,
                                unfocusedBorderColor = Color.Black,
                                errorBorderColor = color
                            ),
                            visualTransformation = if (!passwordVisibility.value) {
                                PasswordVisualTransformation('*')
                            } else {
                                VisualTransformation.None
                            },
                            leadingIcon = {
                                IconButton(
                                    onClick = {
                                        passwordVisibility.value = !passwordVisibility.value
                                    }
                                ) {
                                    if (!passwordVisibility.value) {
                                        Icon(
                                            imageVector = MyIcons.Lock,
                                            contentDescription = ""
                                        )
                                    }

                                    if (passwordVisibility.value) {
                                        Icon(
                                            imageVector = MyIcons.LockOpen,
                                            contentDescription = ""
                                        )
                                    }
                                }
                            }
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp)
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(horizontal = 48.dp),
                            onClick = {
                                if (email.isNotBlank() && password.isNotBlank()) {
                                    viewModel.authUser()
                                }
                                email.ifEmpty {
                                    viewModel.errorStateEmail.value = true
                                    color.value
                                }
                                password.ifEmpty {
                                    viewModel.errorStatePassword.value = true
                                    color.value
                                }

                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.background,
                                contentColor = MaterialTheme.colorScheme.onBackground
                            )
                        ) {
                            Text(
                                text = "Авторизироваться"
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, end = 50.dp)
                            .clickable { onRegisterClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Color.Black)) {
                                    append("Нет аккаунта?")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        color = Color.Black,
                                        textDecoration = TextDecoration.Underline
                                    )
                                ) {
                                    append(" Зарегистрируйтесь!")
                                }
                            }
                        )
                    }
                }
            }

            AuthUserState.Finished -> {
                onProfileClick()
            }
        }
    }
}