package com.example.flavi.view.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class FlaviBottomNavigation<T>(
    val name: String,
    val route: T,
    val icon: ImageVector
)
