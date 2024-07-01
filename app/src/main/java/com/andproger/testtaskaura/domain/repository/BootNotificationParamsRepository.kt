package com.andproger.testtaskaura.domain.repository

import com.andproger.testtaskaura.domain.model.BootNotificationParams
import kotlinx.coroutines.flow.Flow

interface BootNotificationParamsRepository {
    suspend fun save(params: BootNotificationParams)

    fun get(): BootNotificationParams

    fun getFlow(): Flow<BootNotificationParams>
}