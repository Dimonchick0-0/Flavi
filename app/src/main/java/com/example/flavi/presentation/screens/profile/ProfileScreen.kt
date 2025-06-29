package com.example.flavi.presentation.screens.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.flavi.R
import com.example.flavi.presentation.navigation.Screens
import com.example.flavi.presentation.screens.FlaviBottomNavigation

@Composable
fun Profile(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    navHostController: NavHostController,
    onLogOutOfAccountClick: () -> Unit,
    goToSearchMoviesClick: () -> Unit
) {
    
    val state = viewModel.stateProfile.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            val listNavigation = listOf(
                FlaviBottomNavigation("Поиск", Screens.SearchMovie, Icons.Filled.Search),
                FlaviBottomNavigation("Аккаунт", Screens.Profile, Icons.Filled.AccountCircle)
            )
            NavigationBar {
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
                            goToSearchMoviesClick()
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.onBackground)
        ) {
            when (val currentState = state.value) {
                is ProfileState.Initial -> {
                    viewModel.initialUser()
                    Card(
                        modifier = modifier
                            .padding(top = 32.dp, start = 8.dp, end = 8.dp)
                            .fillMaxWidth()
                            .size(100.dp)
                            //Сделать цвет светло-серым
                            .background(
                                color = MaterialTheme.colorScheme.onBackground
                            )
                            .clip(RoundedCornerShape(8.dp))

                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .padding(32.dp),
                                painter = painterResource(R.drawable.ic_launcher_foreground),
                                contentDescription = "Аватарка"
                            )
                            Column(
                                modifier = Modifier.padding(start = 16.dp)
                            ) {
                                Text(
                                    text = currentState.nameUser
                                )
                                Text(
                                    text = currentState.emailUser
                                )
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                ,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        viewModel.logOutOfYourAccount()
                        onLogOutOfAccountClick()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Text(
                        text = "Выйти из аккаунта"
                    )
                }
                Button(
                    onClick = {
                        Log.d("Auth", "Изменение профиля...")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Text(
                        text = "Изменить профиль"
                    )
                }
            }
        }
    }
}