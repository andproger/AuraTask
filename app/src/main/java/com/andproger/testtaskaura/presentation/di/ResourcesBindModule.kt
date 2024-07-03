package com.andproger.testtaskaura.presentation.di

import com.andproger.testtaskaura.presentation.resources.AppResProvider
import com.andproger.testtaskaura.presentation.resources.AppResProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ResourcesBindModule {
    @Binds
    internal abstract fun bindResourcesProvider(
        resourcesProvider: AppResProviderImpl
    ): AppResProvider
}