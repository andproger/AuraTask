package com.andproger.testtaskaura.presentation.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.andproger.testtaskaura.presentation.di.ReceiverEntryPoint
import com.andproger.testtaskaura.presentation.di.ReceiverCoroutineScope
import com.andproger.testtaskaura.presentation.worker.scheduleNextOneTimeWork
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class NotificationDismissReceiver : BroadcastReceiver() {

    @Inject
    @ReceiverCoroutineScope
    lateinit var coroutineScope: CoroutineScope

    override fun onReceive(context: Context, intent: Intent) {
        val hiltEntryPoint = EntryPointAccessors.fromApplication(context, ReceiverEntryPoint::class.java)
        val notificationParamsRepository = hiltEntryPoint.bootNotificationParamsRepository()
        val dismissCountRepository = hiltEntryPoint.dismissCountRepository()

        coroutineScope.launch {
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
