package com.example.flavi.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavi.R

@Composable
fun RegistrationScreen(modifier: Modifier = Modifier) {
    Scaffold(
        containerColor = Color.White
    ) {
        Image(
            painter = painterResource(id = R.drawable.rect1),
            contentDescription = ""
        )
        Image(
            painter = painterResource(id = R.drawable.rect2),
            contentDescription = ""
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 65.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Регистрация аккаунта",
                    fontSize = 28.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontFamily = FontFamily.Serif
                )
                Spacer(modifier = Modifier.height(15.dp))
                TextFieldRegistration(title = "Имя")
                TextFieldRegistration(title = "Почта")
                TextFieldRegistration(title = "Пароль")
                TextFieldRegistration(title = "Подтвердите пароль")
                Spacer(modifier = Modifier.height(25.dp))
                ElevatedButton(
                    onClick = {},
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color.Black,
                        contentColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.reg_btn_text),
                        fontSize = 20.sp
                    )
                }
                ConfiguringTheTextForAuthUser()
            }
        }

    }
}

@Composable
private fun ConfiguringTheTextForAuthUser() {
    Spacer(modifier = Modifier.height(25.dp))
    Text(
        buildAnnotatedString {
            withStyle(
                SpanStyle(fontSize = 17.sp, color = Color.Black)
            ) {
                append("Уже есть аккаунт?")
            }
            withStyle(
                SpanStyle(
                    fontSize = 17.sp,
                    textDecoration = TextDecoration.Underline,
                    color = Color.Black
                )
            ) {
                append(" Авторизуйтесь!")
            }
        }
    )
}

@Composable
private fun TextFieldRegistration(title: String) {
    OutlinedTextField(
        modifier = Modifier.padding(top = 25.dp),
        value = "",
        onValueChange = {},
        label = { Text(
            text = title,
            color = Color.Black
        ) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White
        ),
        shape = RoundedCornerShape(15.dp)
    )
    Spacer(modifier = Modifier.height(15.dp))
}