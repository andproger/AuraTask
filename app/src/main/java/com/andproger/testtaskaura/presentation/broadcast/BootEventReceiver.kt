package com.andproger.testtaskaura.presentation.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.andproger.testtaskaura.data.entity.BootEventEntity
import com.andproger.testtaskaura.domain.model.BootEvent
import com.andproger.testtaskaura.domain.repository.BootEventRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootEventReceiver : BroadcastReceiver() {

    @Inject
    lateinit var bootEventRepository: BootEventRepository

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            //TODO create AppScope and use instead of GlobalScope
            GlobalScope.launch(Dispatchers.IO) {
                bootEventRepository.saveBootEvent(BootEvent(System.currentTimeMillis()))
            }
        }
    }
}