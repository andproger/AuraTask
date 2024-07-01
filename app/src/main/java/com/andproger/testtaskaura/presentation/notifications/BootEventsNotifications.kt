package com.andproger.testtaskaura.presentation.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.andproger.testtaskaura.presentation.broadcast.NotificationDismissReceiver

fun Context.createBootEventsNotificationChannel() {
    //TODO string resources
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Boot Events"
        val descriptionText = "Channel for boot events notifications"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(BOOT_EVENTS_CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun Context.showBootEventNotification(text: String) {
    createBootEventsNotificationChannel()

    //TODO string resources
    val notificationManager = NotificationManagerCompat.from(this)

    val deleteIntent = Intent(this, NotificationDismissReceiver::class.java)
    val deletePendingIntent =
        PendingIntent.getBroadcast(
            this,
            DELETE_BOOT_NOTIFICATION_REQUEST_CODE,
            deleteIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

    val notification = NotificationCompat.Builder(this, BOOT_EVENTS_CHANNEL_ID)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Boot Event")
        .setContentText(text)
        .setDeleteIntent(deletePendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()

    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
        notificationManager.notify(1, notification)
    }
}

const val BOOT_EVENTS_CHANNEL_ID = "BOOT_EVENTS_CHANNEL_ID"
const val DELETE_BOOT_NOTIFICATION_REQUEST_CODE = 112

object NotificationDefaults {
    const val DISMISS_LIMIT_COUNT = 5
    const val INTERVAL_PER_DISMISSAL_MIN = 20
}