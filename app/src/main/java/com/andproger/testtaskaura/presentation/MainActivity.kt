package com.andproger.testtaskaura.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.compose.rememberNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.andproger.testtaskaura.presentation.notifications.requestNotificationPermission
import com.andproger.testtaskaura.presentation.notifications.showBootEventNotification
import com.andproger.testtaskaura.presentation.theme.TestTaskAuraTheme
import com.andproger.testtaskaura.presentation.worker.NotificationWorker
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


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
        requestNotificationPermission(requestPermissionLauncher) {
            //TODO remove
            showBootEventNotification("test")
        }
        startBootEventWorker()
    }

    private fun startBootEventWorker() {
        // TODO constant 15 min
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            // TODO constant
            "boot_event_notification",
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}