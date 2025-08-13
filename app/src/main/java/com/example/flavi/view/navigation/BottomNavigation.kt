package com.example.flavi.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.flavi.view.ui.theme.MyIcons

object BottomNavigation {

    @Composable
    fun BottomNav(
        navHostController: NavHostController,
        containerColor: Color = NavigationBarDefaults.containerColor
    ) {
        val listNavigation = listOf(
            FlaviBottomNavigation("Поиск", Screens.SearchMovie, Icons.Filled.Search),
            FlaviBottomNavigation("Аккаунт", Screens.Profile, Icons.Filled.AccountCircle),
            FlaviBottomNavigation("Избранные", Screens.Favorite, MyIcons.Favorite_Movies)
        )
        NavigationBar(
            containerColor = containerColor
        ) {
            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            listNavigation.forEach { flaviNavItem ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = flaviNavItem.icon,
                            contentDescription = flaviNavItem.name
                        )
                    },
                    label = { Text(text = flaviNavItem.name) },
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
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = Color.DarkGray,
                        unselectedTextColor = Color.DarkGray,
                        selectedIconColor = MaterialTheme.colorScheme.onBackground,
                        selectedTextColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            }
        }
    }
}