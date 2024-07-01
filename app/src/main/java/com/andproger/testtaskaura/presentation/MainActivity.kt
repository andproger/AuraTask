package com.andproger.testtaskaura.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.compose.rememberNavController
import com.andproger.testtaskaura.presentation.notifications.requestNotificationPermission
import com.andproger.testtaskaura.presentation.notifications.scheduleBootEventPeriodicWork
import com.andproger.testtaskaura.presentation.theme.TestTaskAuraTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { _: Boolean -> }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestTaskAuraTheme {
                val navController = rememberNavController()
                MainGraph(navController)
            }
        }
        requestNotificationPermission(requestPermissionLauncher)
        scheduleBootEventPeriodicWork()
    }
}