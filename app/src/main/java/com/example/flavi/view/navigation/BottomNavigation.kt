package com.example.flavi.view.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShortNavigationBar
import androidx.compose.material3.ShortNavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.flavi.view.ui.theme.MyIcons

object BottomNavigation {

    @Composable
    fun BottomNav(
        navHostController: NavHostController
    ) {
        val listNavigation = listOf(
            FlaviBottomNavigation("Поиск", Screens.SearchMovie.route, MyIcons.Search),
            FlaviBottomNavigation("Аккаунт", Screens.Profile.route, MyIcons.AccountCircle),
            FlaviBottomNavigation("Избранные", Screens.Favorite.route, MyIcons.Favorite_Movies)
        )
        ShortNavigationBar (
            containerColor = MaterialTheme.colorScheme.secondary
        ) {
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            listNavigation.forEach { flaviNavItem ->
                ShortNavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = flaviNavItem.icon,
                            contentDescription = flaviNavItem.name,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = flaviNavItem.name,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    selected = navBackStackEntry?.destination?.hierarchy?.any {
                        it.hasRoute(flaviNavItem.route::class)
                    } == true,
                    onClick = {
                        navHostController.navigate(flaviNavItem.route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}