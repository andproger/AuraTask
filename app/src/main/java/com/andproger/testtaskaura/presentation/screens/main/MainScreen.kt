package com.andproger.testtaskaura.presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MainScreen() {
    val viewModel: MainViewModel = hiltViewModel()

    val state by viewModel.state.collectAsStateWithLifecycle()

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        //TODO replace with list
        Text(
            text = state.summaryText
        )
        NotificationsSettings(
            state,
            viewModel::onTotalDismissalsAllowedChanged,
            viewModel::onIntervalBetweenDismissalsChanged,
            viewModel::saveSettings
        )
    }
}

//TODO move to separate screen
@Composable
fun NotificationsSettings(
    state: MainState,
    onTotalDismissalsAllowedChanged: (String) -> Unit,
    onIntervalBetweenDismissalsChanged: (String) -> Unit,
    saveSettings: () -> Unit
) {
    //TODO string resources
    val totalDismissalsAllowed = state.totalDismissalsAllowed
    val intervalBetweenDismissals = state.intervalBetweenDismissals

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        NumberInputField(
            label = "Total dismissals allowed",
            value = totalDismissalsAllowed?.toString() ?: "",
            onValueChange = { newValue -> onTotalDismissalsAllowedChanged(newValue) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        NumberInputField(
            label = "Interval between dismissals",
            value = intervalBetweenDismissals?.toString() ?: "",
            onValueChange = { newValue -> onIntervalBetweenDismissalsChanged(newValue) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { saveSettings() },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save")
        }
    }
}

@Composable
fun NumberInputField(label: String, value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth()
    )
}