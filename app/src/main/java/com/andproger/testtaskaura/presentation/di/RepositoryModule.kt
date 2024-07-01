package com.andproger.testtaskaura.presentation.di

import com.andproger.testtaskaura.data.repository.BootEventRepositoryImpl
import com.andproger.testtaskaura.domain.repository.BootEventRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryBindModule {
    @Binds
    abstract fun bindBootEventsRepository(bootEventsRepositoryImpl: BootEventRepositoryImpl): BootEventRepository
}