package com.andproger.testtaskaura.presentation.di

import android.content.Context
import com.andproger.testtaskaura.data.repository.BootEventRepositoryImpl
import com.andproger.testtaskaura.data.repository.BootNotificationParamsRepositoryImpl
import com.andproger.testtaskaura.data.repository.DismissedNotificationsCountRepositoryImpl
import com.andproger.testtaskaura.domain.model.BootNotificationParams
import com.andproger.testtaskaura.domain.repository.BootEventRepository
import com.andproger.testtaskaura.domain.repository.BootNotificationParamsRepository
import com.andproger.testtaskaura.domain.repository.DismissedNotificationsCountRepository
import com.andproger.testtaskaura.domain.usecase.GetBootEventsSummaryUseCase
import com.andproger.testtaskaura.domain.usecase.GetBootEventsSummaryUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseBindModule {
    @Binds
    abstract fun bindGetBootEventsSummaryUseCase(useCaseImpl: GetBootEventsSummaryUseCaseImpl): GetBootEventsSummaryUseCase
}