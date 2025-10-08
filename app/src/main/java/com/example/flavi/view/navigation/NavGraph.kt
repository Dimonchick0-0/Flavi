package com.example.flavi.view.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.flavi.view.screens.auth.AuthUser
import com.example.flavi.view.screens.favorite.FavoriteScreen
import com.example.flavi.view.screens.movieDetail.MovieDetail
import com.example.flavi.view.screens.movieDetail.poster.PosterScreen
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
        Screens.Auth.route
    } else {
        Screens.SearchMovie.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screens.Register.route) {
            RegistrationScreen(
                onRegisterClick = {
                    navController.navigate(route = Screens.Auth.route)
                }
            )
        }
        composable(Screens.Auth.route) {
            AuthUser(
                onProfileClick = {
                    navController.navigate(route = Screens.SearchMovie.route)
                },
                onRegisterClick = {
                    navController.navigate(route = Screens.Register.route)
                }
            )
        }
        composable(Screens.Profile.route) {
            Profile(
                navHostController = navController,
                onLogOutOfAccountClick = {
                    navController.navigate(
                        route = Screens.Auth.route,
                        navOptions = navOptions {
                            popUpTo(route = Screens.Profile.route) {
                                inclusive = true
                            }
                        }
                    )
                }
            )
        }

        composable(Screens.SearchMovie.route) {
            SearchMovie(
                navHostController = navController,
                onClickToMovieDetailScreen = {
                    navController.navigate(Screens.MovieDetail.createRoute(it.filmId))
                }
            )
        }
        composable(Screens.Favorite.route) {
            FavoriteScreen(
                navHostController = navController,
                onClickToMovieDetailScreen = {
                    navController.navigate(Screens.MovieDetail.createRoute(it.filmId))
                }
            )
        }
        composable(Screens.MovieDetail.route) {
            val filmId = it.arguments?.getString("film_id")?.toInt() ?: 0
            MovieDetail(
                filmId = filmId,
                loadAllImageMovieClick = {
                    navController.navigate(Screens.Poster.createRoute(filmId))
                }
            )
        }
        composable(Screens.Poster.route) {
            val filmId = getArguments(it)
            PosterScreen(
                filmId = filmId
            )
        }
    }
}

private fun getArguments(navBackStackEntry: NavBackStackEntry): Int {
    return navBackStackEntry.arguments?.getString("film_id")?.toInt() ?: 0
}

@Serializable
sealed class Screens(val route: String) {
    @Serializable
    data object Register : Screens("register")

    @Serializable
    data object Auth : Screens("auth")

    @Serializable
    data object Profile : Screens("profile")

    @Serializable
    data object SearchMovie : Screens("search_movie")

    @Serializable
    data object Favorite : Screens("favorite")

    @Serializable
    data object MovieDetail: Screens("movie_detail/{film_id}") {

        fun createRoute(filmId: Int): String {
            return "movie_detail/$filmId"
        }

    }

    @Serializable
    data object Poster: Screens(route = "image_screen/{film_id}") {
        fun createRoute(filmId: Int): String {
            return "image_screen/$filmId"
        }
    }
}
