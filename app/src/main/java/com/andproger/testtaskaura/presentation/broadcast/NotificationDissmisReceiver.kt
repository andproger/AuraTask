package com.andproger.testtaskaura.presentation.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.andproger.testtaskaura.presentation.di.BootEventRepositoryEntryPoint
import com.andproger.testtaskaura.presentation.worker.scheduleNextNotification
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NotificationDismissReceiver : BroadcastReceiver() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent) {
        val hiltEntryPoint = EntryPointAccessors.fromApplication(context, BootEventRepositoryEntryPoint::class.java)
        val bootRepository = hiltEntryPoint.bootEventRepository()

        //TODO create AppScope and use instead of GlobalScope
        GlobalScope.launch(Dispatchers.IO) {
            val dismissCount = bootRepository.incrementDismissCount()
            val nextInterval = if (dismissCount > 5) {
                15 * 60 * 1000L // 15 minutes in milliseconds
            } else {
                dismissCount * 20 * 60 * 1000L // Dismiss count * 20 minutes in milliseconds
            }

            scheduleNextNotification(context, nextInterval)
        }
    }
}
