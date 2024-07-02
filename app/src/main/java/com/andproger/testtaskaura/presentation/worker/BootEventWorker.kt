package com.andproger.testtaskaura.presentation.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.andproger.testtaskaura.domain.model.BootNotificationParams
import com.andproger.testtaskaura.domain.repository.BootEventRepository
import com.andproger.testtaskaura.presentation.notifications.showBootEventNotification
import com.andproger.testtaskaura.presentation.worker.NotificationDefaults.REPEAT_INTERVAL_MIN
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

fun calculateNextInterval(params: BootNotificationParams, dismissCount: Int): Long {
    return if (dismissCount > params.totalDismissalsAllowed) {
        REPEAT_INTERVAL_MIN * 60 * 1000L // 15 minutes in milliseconds
    } else {
        dismissCount * params.intervalBetweenDismissalsMin * 60 * 1000L // Dismiss count * 20 minutes in milliseconds
    }
}

fun scheduleNextOneTimeWork(
    context: Context,
    params: BootNotificationParams,
    dismissCount: Int
) {
    val delay = calculateNextInterval(params, dismissCount)

    val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context).enqueue(workRequest)
}

fun Context.scheduleBootEventPeriodicWork() {
    val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(REPEAT_INTERVAL_MIN, TimeUnit.MINUTES)
        .build()

    WorkManager.getInstance(this).enqueueUniquePeriodicWork(
        WORK_NAME,
        ExistingPeriodicWorkPolicy.UPDATE,
        workRequest
    )
}

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val bootEventRepository: BootEventRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val context = applicationContext
        val bootEvents = bootEventRepository.getBootEvents()

        context.showBootEventNotification(bootEvents)
        return Result.success()
    }
}

const val WORK_NAME = "boot_event_notification"

object NotificationDefaults {
    const val REPEAT_INTERVAL_MIN = 15L
}