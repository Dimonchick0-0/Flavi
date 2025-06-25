package com.example.flavi.presentation.screens.profile

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Profile(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = viewModel()
) {
    val state = viewModel.stateProfile
    Text(
        text = state.value
    )
    Log.d("Auth", state.value)
}