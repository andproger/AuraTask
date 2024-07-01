package com.andproger.testtaskaura.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.andproger.testtaskaura.presentation.screens.main.MainScreen

@Composable
fun MainGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "MainScreen",
    ) {
        composable(
            route = "MainScreen",
        ) {
            MainScreen()
        }
    }
}