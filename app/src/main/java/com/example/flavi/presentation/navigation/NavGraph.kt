package com.example.flavi.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.flavi.presentation.screens.auth.AuthUser
import com.example.flavi.presentation.screens.profile.Profile
import com.example.flavi.presentation.screens.registration.RegistrationScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun NavGraph() {
    val currentUser = Firebase.auth.currentUser
    val navController = rememberNavController()
    val startDestination = if (currentUser == null) {
        FlaviScreens.AuthScreen.route
    } else {
        FlaviScreens.ProfileScreen.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
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
                },
                onRegisterClick = {
                    navController.navigate(FlaviScreens.RegistrationScreen.route)
                }
            )
        }
        composable(FlaviScreens.ProfileScreen.route) {
            Profile(
                onLogOutOfAccountClick = {
                    navController.navigate(
                        route = FlaviScreens.AuthScreen.route,
                        navOptions =  navOptions {
                            popUpTo(route = FlaviScreens.ProfileScreen.route) {
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }
    }
}

sealed class FlaviScreens(val route: String) {
    data object RegistrationScreen: FlaviScreens("register")

    data object AuthScreen: FlaviScreens("auth")

    data object ProfileScreen: FlaviScreens("profile")
}