package com.andproger.testtaskaura.presentation.screens.notification_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun NotificationsSettingsScreen() {
    val viewModel: NotificationSettingsViewModel = hiltViewModel()

    val totalDismissalsAllowedState by viewModel.totalDismissalsAllowedState.collectAsStateWithLifecycle()
    val intervalBetweenDismissalsMinState by viewModel.intervalBetweenDismissalsMinState.collectAsStateWithLifecycle()

    NotificationSettingsContent(
        totalDismissalsAllowedState,
        intervalBetweenDismissalsMinState,
        viewModel::onTotalDismissalsAllowedChanged,
        viewModel::onIntervalBetweenDismissalsChanged,
        viewModel::saveSettings
    )
}

@Composable
fun NotificationSettingsContent(
    totalDismissalsAllowed: Int?,
    intervalBetweenDismissalsMin: Int?,
    onTotalDismissalsAllowedChanged: (String) -> Unit,
    onIntervalBetweenDismissalsChanged: (String) -> Unit,
    onSaveSettings: () -> Unit
) {
    val isSaveEnabled = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    //TODO string resources
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = totalDismissalsAllowed?.toString() ?: "",
            onValueChange = {
                onTotalDismissalsAllowedChanged(it)
                isSaveEnabled.value = true
            },
            label = { Text("Total dismissals allowed") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            value = intervalBetweenDismissalsMin?.toString() ?: "",
            onValueChange = {
                onIntervalBetweenDismissalsChanged(it)
                isSaveEnabled.value = true
            },
            label = { Text("Interval between dismissals") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                focusManager.clearFocus()
                isSaveEnabled.value = false
                onSaveSettings()
            },
            modifier = Modifier.align(Alignment.End),
            enabled = isSaveEnabled.value
        ) {
            Text("Save")
        }
    }
}

