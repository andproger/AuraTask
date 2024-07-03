package com.andproger.testtaskaura.presentation.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.andproger.testtaskaura.domain.date.CurrentDateProvider
import com.andproger.testtaskaura.domain.model.BootEvent
import com.andproger.testtaskaura.domain.repository.BootEventRepository
import com.andproger.testtaskaura.presentation.di.ReceiverCoroutineScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootEventReceiver : BroadcastReceiver() {

    @Inject
    lateinit var bootEventRepository: BootEventRepository

    @Inject
    lateinit var currentDateProvider: CurrentDateProvider

    @Inject
    @ReceiverCoroutineScope
    lateinit var coroutineScope: CoroutineScope

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED || intent?.action == "com.andproger.testtaskaura.TEST_BOOT_COMPLETED") {
            coroutineScope.launch {
                val now = currentDateProvider.provideCurrentTimestamp()
                bootEventRepository.saveBootEvent(BootEvent(now))
            }
        }
    }
}