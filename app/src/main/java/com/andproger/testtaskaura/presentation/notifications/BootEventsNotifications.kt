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
import com.andproger.testtaskaura.R
import com.andproger.testtaskaura.domain.model.BootEvent
import com.andproger.testtaskaura.presentation.broadcast.NotificationDismissReceiver
import com.andproger.testtaskaura.presentation.utils.toFormattedDate
import com.andproger.testtaskaura.presentation.utils.toFormattedDuration

fun Context.createBootEventsNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = getString(R.string.boot_events)
        val descriptionText = getString(R.string.boot_events_chanel_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(BOOT_EVENTS_CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun createBootNotificationText(context: Context, bootEvents: List<BootEvent>): String {
    return when {
        bootEvents.isEmpty() -> context.getString(R.string.no_boots_detected)
        bootEvents.size == 1 -> context.getString(
            R.string.the_boot_was_detected,
            bootEvents.first().timestamp.toFormattedDate()
        )
        else -> {
            val lastBootTime = bootEvents[0].timestamp
            val secondLastBootTime = bootEvents[1].timestamp
            val delta = lastBootTime - secondLastBootTime
            context.getString(R.string.last_boots_time_delta, delta.toFormattedDuration())
        }
    }
}

fun Context.showBootEventNotification(bootEvents: List<BootEvent>) {
    val text = createBootNotificationText(this, bootEvents)
    createBootEventsNotificationChannel()

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
        .setContentTitle(getString(R.string.boot_events))
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