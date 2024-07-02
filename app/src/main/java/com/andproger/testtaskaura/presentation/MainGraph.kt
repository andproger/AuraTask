package com.andproger.testtaskaura.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.andproger.testtaskaura.presentation.screens.NavRoutes
import com.andproger.testtaskaura.presentation.screens.main.MainScreen
import com.andproger.testtaskaura.presentation.screens.notification_settings.NotificationsSettingsScreen

@Composable
fun MainGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.MainScreen.route,
    ) {
        composable(
            route = NavRoutes.MainScreen.route,
        ) {
            MainScreen(navController)
        }
        composable(
            route = NavRoutes.NotificationSettings.route,
        ) {
            NotificationsSettingsScreen()
        }
    }
}