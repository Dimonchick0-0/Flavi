package com.example.flavi.view.screens.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RowScope.RegistrationTextField(
    modifier: Modifier = Modifier,
    value: String,
    text: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    leadingIcon: @Composable (() -> Unit)? = null,
    onQueryChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier
            .padding(top = 24.dp, start = 8.dp, end = 8.dp)
            .weight(1f)
            .sizeIn(maxWidth = 200.dp),
        maxLines = 1,
        value = value,
        onValueChange = {
            onQueryChange(it)
        },
        label = {
            Text(
                text = text,
                color = Color.Black,
                fontSize = 14.sp
            )
        },
        keyboardOptions = keyboardOptions,
        leadingIcon = leadingIcon,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black
        ),
        visualTransformation = visualTransformation,
        isError = isError,
    )
}