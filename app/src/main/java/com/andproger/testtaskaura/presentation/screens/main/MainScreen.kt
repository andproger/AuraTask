package com.andproger.testtaskaura.presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun MainScreen(
    navController: NavController
) {
    val viewModel: MainViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = state.summaryText,
                modifier = Modifier.weight(1f)
            )

            Button(
                onClick = {
                    navController.navigate("notification_settings")
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Notification Settings")
            }
        }
    }
}