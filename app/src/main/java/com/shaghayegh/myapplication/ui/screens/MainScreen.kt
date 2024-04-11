package com.shaghayegh.myapplication.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shaghayegh.myapplication.ui.viewmodel.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel) {

    val items = listOf(Screen.MapScreen, Screen.RouteScreen, Screen.AlertScreen)
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painterResource(id = screen.icon),
                                contentDescription = null
                            )
                        },
                        label = { Text(screen.label) },

                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo = navController.graph.startDestinationId
                                launchSingleTop = true
                            }
                        },
                        selected = currentRoute == screen.route,
                        selectedContentColor = Color.Yellow,
                        unselectedContentColor = Color.White// Color for unselected item
                        // unselectedContentColor = Color.White
                    )


                }


            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.MapScreen.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.MapScreen.route) { MapScreen(mainViewModel) }
            composable(Screen.RouteScreen.route) { RouteScreen() }
            composable(Screen.AlertScreen.route) { AlertScreen(mainViewModel) }
        }
    }
}