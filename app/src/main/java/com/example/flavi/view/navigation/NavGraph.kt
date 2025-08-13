package com.example.flavi.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.flavi.view.screens.auth.AuthUser
import com.example.flavi.view.screens.favorite.FavoriteScreen
import com.example.flavi.view.screens.profile.Profile
import com.example.flavi.view.screens.registration.RegistrationScreen
import com.example.flavi.view.screens.searchMovie.SearchMovie
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.serialization.Serializable

@Composable
fun NavGraph() {
    val currentUser = Firebase.auth.currentUser
    val navController = rememberNavController()
    val startDestination = if (currentUser == null) {
        Screens.Auth
    } else {
        Screens.SearchMovie
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Screens.Register> {
            RegistrationScreen(
                onRegisterClick = {
                    navController.navigate(route = Screens.Auth)
                }
            )
        }
        composable<Screens.Auth> {
            AuthUser(
                onProfileClick = {
                    navController.navigate(route = Screens.SearchMovie)
                },
                onRegisterClick = {
                    navController.navigate(route = Screens.Register)
                }
            )
        }
        composable<Screens.Profile> {
            Profile(
                navHostController = navController,
                onLogOutOfAccountClick = {
                    navController.navigate(
                        route = Screens.Auth,
                        navOptions = navOptions {
                            popUpTo(route = Screens.Profile) {
                                inclusive = true
                            }
                        }
                    )
                },
                goToSearchMoviesClick = {
                    navController.navigate(route = Screens.Profile)
                    checkingForAdditionalScreens(navController)
                }
            )
        }
        composable<Screens.SearchMovie> {
            SearchMovie(
                navHostController = navController,
                onClickToFavoriteScreen = {
                    navController.navigate(Screens.Favorite)
                },
                onClickToProfileScreen = {
                    navController.navigate(route = Screens.Profile)
                }
            )
        }
        composable<Screens.Favorite> {
            FavoriteScreen(
                navHostController = navController,
                onClickToSearchMovie = {
                    navController.navigate(route = Screens.SearchMovie)
                    checkingForAdditionalScreens(navController)
                },
                onClickToProfileScreen = {
                    navController.navigate(route = Screens.Profile)
                }
            )
        }
    }
}

private fun checkingForAdditionalScreens(
    navController: NavHostController
) {
    if (navController.popBackStack()) {
        navController.popBackStack()
    }
}

@Serializable
sealed interface Screens {
    @Serializable
    object Register

    @Serializable
    object Auth

    @Serializable
    object Profile

    @Serializable
    object SearchMovie

    @Serializable
    object Favorite
}
