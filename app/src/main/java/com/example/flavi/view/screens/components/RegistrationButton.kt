package com.example.flavi.view.screens.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RegistrationButton(
    modifier: Modifier = Modifier,
    buttonOnClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = buttonOnClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Text(
            text = "Зарегистрироваться"
        )
    }
}