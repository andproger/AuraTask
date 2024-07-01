package com.andproger.testtaskaura.domain.repository

import com.andproger.testtaskaura.domain.model.BootEvent


interface BootEventRepository {
    suspend fun saveBootEvent(bootEvent: BootEvent)

    suspend fun getBootEvents(): List<BootEvent>

    suspend fun incrementDismissCount(): Int
}