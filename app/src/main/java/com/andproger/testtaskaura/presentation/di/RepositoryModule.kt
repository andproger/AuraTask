package com.andproger.testtaskaura.presentation.di

import android.content.Context
import com.andproger.testtaskaura.data.repository.BootEventRepositoryImpl
import com.andproger.testtaskaura.data.repository.BootNotificationParamsRepositoryImpl
import com.andproger.testtaskaura.data.repository.DismissedNotificationsCountRepositoryImpl
import com.andproger.testtaskaura.domain.model.BootNotificationParams
import com.andproger.testtaskaura.domain.repository.BootEventRepository
import com.andproger.testtaskaura.domain.repository.BootNotificationParamsRepository
import com.andproger.testtaskaura.domain.repository.DismissedNotificationsCountRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryBindModule {

    @Binds
    @Singleton
    abstract fun bindBootEventsRepository(bootEventsRepositoryImpl: BootEventRepositoryImpl): BootEventRepository

    @Binds
    @Singleton
    abstract fun bindDismissedNotificationsCountRepository(repositoryImpl: DismissedNotificationsCountRepositoryImpl): DismissedNotificationsCountRepository
}

@InstallIn(SingletonComponent::class)
@Module
object RepositoryProvideModule {

    @Provides
    @Singleton
    fun bindBootNotificationRepository(@ApplicationContext context: Context): BootNotificationParamsRepository {
        return BootNotificationParamsRepositoryImpl(context, BootNotificationParams.default)
    }
}