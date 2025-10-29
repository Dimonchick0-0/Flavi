package com.example.flavi.view.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.flavi.viewmodel.UpdateAccountViewModel

@Composable
fun UpdateAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdateAccountViewModel = hiltViewModel()
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colorScheme.surface
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(height = 32.dp))
            UpdateProfile(
                actualDataInProfile = viewModel.getCurrentUser().displayName!!,
                updateProfileClick = { viewModel.updateProfile(name = it) }
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
