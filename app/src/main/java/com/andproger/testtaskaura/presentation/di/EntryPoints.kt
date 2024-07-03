package com.andproger.testtaskaura.presentation.di

import com.andproger.testtaskaura.domain.repository.BootEventRepository
import com.andproger.testtaskaura.domain.repository.BootNotificationParamsRepository
import com.andproger.testtaskaura.domain.repository.DismissedNotificationsCountRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ReceiverEntryPoint {
    fun bootEventRepository(): BootEventRepository
    fun bootNotificationParamsRepository(): BootNotificationParamsRepository
    fun dismissCountRepository(): DismissedNotificationsCountRepository
}