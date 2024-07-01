package com.andproger.testtaskaura.presentation

import android.Manifest
import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
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
        requestNotificationPermission()
        createNotificationChannel()

        showTestNotification()
    }

    private fun showTestNotification() {
        val notificationManager = NotificationManagerCompat.from(this)
        val notification = NotificationCompat.Builder(this, "NETWORK_CHANNEL_ID")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("ContentTitle")
            .setContentText("Test")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        if (ActivityCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, notification)
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(this, "Permission already granted", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    requestPermissionLauncher.launch(POST_NOTIFICATIONS)
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Network State Channel"
            val descriptionText = "Channel for network state change notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("NETWORK_CHANNEL_ID", name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}