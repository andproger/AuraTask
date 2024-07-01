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

            //TODO move logic and schedule call to some domain class
            if (dismissCount > params.totalDismissalsAllowed) {
                dismissCountRepository.reset()
            }
            scheduleNextOneTimeWork(context, params, dismissCount)
        }
    }
}
