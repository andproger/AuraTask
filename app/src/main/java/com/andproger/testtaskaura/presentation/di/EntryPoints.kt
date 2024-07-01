package com.andproger.testtaskaura.presentation.di

import com.andproger.testtaskaura.domain.repository.BootEventRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface BootEventRepositoryEntryPoint {
    fun bootEventRepository(): BootEventRepository
}