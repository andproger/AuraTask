package com.andproger.testtaskaura.domain.repository

import com.andproger.testtaskaura.domain.model.BootNotificationParams

interface BootNotificationParamsRepository {
    suspend fun save(params: BootNotificationParams)

    suspend fun get(): BootNotificationParams
}