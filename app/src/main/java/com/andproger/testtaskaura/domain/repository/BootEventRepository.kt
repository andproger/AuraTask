package com.andproger.testtaskaura.domain.repository

import com.andproger.testtaskaura.domain.model.BootEvent
import kotlinx.coroutines.flow.Flow


interface BootEventRepository {
    suspend fun saveBootEvent(bootEvent: BootEvent)

    suspend fun getBootEvents(): List<BootEvent>

    fun getBootEventsFlow(): Flow<List<BootEvent>>
}