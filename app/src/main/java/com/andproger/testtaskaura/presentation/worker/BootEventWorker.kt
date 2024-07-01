package com.andproger.testtaskaura.presentation.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.andproger.testtaskaura.domain.repository.BootEventRepository
import com.andproger.testtaskaura.presentation.notifications.showBootEventNotification
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


fun scheduleNextOneTimeWork(context: Context, delay: Long) {
    val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context).enqueue(workRequest)
}

@HiltWorker
class NotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val bootEventRepository: BootEventRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val context = applicationContext
        val bootEvents = bootEventRepository.getBootEvents()

        //TODO string resources
        //TODO move this logic to a separate file/class
        val notificationText = when {
            bootEvents.isEmpty() -> "No boots detected"
            bootEvents.size == 1 -> "The boot was detected = ${bootEvents.first().timestamp.toFormattedDate()}"
            else -> {
                val lastBootTime = bootEvents[0].timestamp
                val secondLastBootTime = bootEvents[1].timestamp
                val delta = lastBootTime - secondLastBootTime
                "Last boots time delta = ${delta.toFormattedDuration()}"
            }
        }

        context.showBootEventNotification(notificationText)
        return Result.success()
    }

    private fun Long.toFormattedDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(Date(this))
    }

    private fun Long.toFormattedDuration(): String {
        val seconds = this / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        return "$hours hours, ${minutes % 60} minutes"
    }
}
