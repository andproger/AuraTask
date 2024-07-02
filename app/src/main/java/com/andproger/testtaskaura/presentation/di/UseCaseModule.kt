package com.andproger.testtaskaura.presentation.di

import com.andproger.testtaskaura.domain.usecase.GetBootEventsSummaryUseCase
import com.andproger.testtaskaura.domain.usecase.GetBootEventsSummaryUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseBindModule {
    @Binds
    abstract fun bindGetBootEventsSummaryUseCase(useCaseImpl: GetBootEventsSummaryUseCaseImpl): GetBootEventsSummaryUseCase
}