package com.andproger.testtaskaura.presentation.di


import com.andproger.testtaskaura.domain.date.AppDateFormatter
import com.andproger.testtaskaura.domain.date.AppDateFormatterImpl
import com.andproger.testtaskaura.domain.date.CurrentDateProvider
import com.andproger.testtaskaura.domain.date.CurrentDateProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DateModule {
    @Binds
    internal abstract fun bindDateFormatter(
        dateFormatter: AppDateFormatterImpl
    ): AppDateFormatter

    @Binds
    internal abstract fun bindCurrentDateProvider(
        currentDateProvider: CurrentDateProviderImpl
    ): CurrentDateProvider
}
