package com.andproger.testtaskaura.presentation.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.andproger.testtaskaura.presentation.di.BootEventRepositoryEntryPoint
import com.andproger.testtaskaura.presentation.worker.scheduleNextOneTimeWork
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NotificationDismissReceiver : BroadcastReceiver() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent) {
        val hiltEntryPoint = EntryPointAccessors.fromApplication(context, BootEventRepositoryEntryPoint::class.java)
        val notificationParamsRepository = hiltEntryPoint.bootNotificationParamsRepository()
        val dismissCountRepository = hiltEntryPoint.dismissCountRepository()


        //TODO create AppScope and use instead of GlobalScope
        GlobalScope.launch(Dispatchers.IO) {
            val params = notificationParamsRepository.get()
            val dismissCount = dismissCountRepository.increment()

            //TODO const 15 min
            val nextInterval = if (dismissCount > params.totalDismissalsAllowed) {
                15 * 60 * 1000L // 15 minutes in milliseconds
            } else {
                dismissCount * params.intervalBetweenDismissalsMin * 60 * 1000L // Dismiss count * 20 minutes in milliseconds
            }

            scheduleNextOneTimeWork(context, nextInterval)
        }
    }
}
