package com.example.flavi.view.screens.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flavi.viewmodel.UpdateAccountViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun UpdateAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdateAccountViewModel = hiltViewModel(),
    goToProfileScreenClick: () -> Unit
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        val coroutineScope = rememberCoroutineScope()
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(height = 32.dp))
            UpdateProfile(
                actualDataInProfile = viewModel.getCurrentUser().displayName!!,
                updateProfileClick = { viewModel.updateProfile(name = it) }
            )
            viewModel.apply {
                if (showIndicatorIfProfileIsUpdated) {
                    CardProgress(
                        showIndicatorIfProfileIsUpdated = showIndicatorIfProfileIsUpdated
                    )
                }
                DialogIfProfileUserIsUpdate(
                    viewModel = viewModel,
                    goToProfileScreenClick = goToProfileScreenClick
                )
            }


        }
    }

}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun CardProgress(
    modifier: Modifier = Modifier,
    showIndicatorIfProfileIsUpdated: Boolean
) {
    val coroutineScope = rememberCoroutineScope()

    if (showIndicatorIfProfileIsUpdated) {
        CircularProgressIndicator(
            modifier = modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
        )
        coroutineScope.launch {
            delay(2000)
        }
    }
}

@Composable
private fun DialogIfProfileUserIsUpdate(
    viewModel: UpdateAccountViewModel,
    goToProfileScreenClick: () -> Unit
) {
    viewModel.apply {
        if (showDialogIfProfileIsUpdated) {
            AlertDialog(
                backgroundColor = MaterialTheme.colorScheme.surface,
                onDismissRequest = {
                    showDialogIfProfileIsUpdated = false
                },
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(),
                        text = "Обновление аккаунта",
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                text = {
                    Text(
                        text = "Обновление закончилось успешно!",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp
                    )
                },
                confirmButton = {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        onClick = {
                            showDialogIfProfileIsUpdated = false
                            goToProfileScreenClick()
                        }
                    ) {
                        Text(
                            text = "Вернуться на экран аккаунта",
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 14.sp
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun UpdateProfile(
    modifier: Modifier = Modifier,
    actualDataInProfile: String,
    updateProfileClick: (String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        backgroundColor = MaterialTheme.colorScheme.surface
    ) {
        var newDataInProfile by remember { mutableStateOf("") }
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Актуальное имя: $actualDataInProfile",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp
                )
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                value = newDataInProfile,
                onValueChange = { newDataInProfile = it }
            )
            Spacer(modifier = Modifier.height(height = 8.dp))
            Button(
                onClick = {
                    updateProfileClick(newDataInProfile)
                }
            ) {
                Text(
                    text = "Сохранить изменения",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
