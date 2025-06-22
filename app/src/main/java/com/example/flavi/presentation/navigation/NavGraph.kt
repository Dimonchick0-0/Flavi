package com.example.flavi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flavi.presentation.screens.auth.AuthUser
import com.example.flavi.presentation.screens.profile.Profile
import com.example.flavi.presentation.screens.registration.RegistrationScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = FlaviScreens.AuthScreen.route
    ) {
        composable(FlaviScreens.RegistrationScreen.route) {
            RegistrationScreen(
                onRegisterClick = {
                    navController.navigate(FlaviScreens.AuthScreen.route)
                }
            )
        }
        composable(FlaviScreens.AuthScreen.route) {
            AuthUser(
                onProfileClick = {
                    navController.navigate(FlaviScreens.ProfileScreen.route)
                }
            )
        }
        composable(FlaviScreens.ProfileScreen.route) {
            Profile()
        }
    }
}

sealed class FlaviScreens(val route: String) {
    data object RegistrationScreen: FlaviScreens("register")

    data object AuthScreen: FlaviScreens("auth")

    data object ProfileScreen: FlaviScreens("profile")
}