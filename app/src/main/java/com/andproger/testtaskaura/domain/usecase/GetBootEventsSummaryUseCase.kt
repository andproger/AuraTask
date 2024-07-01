package com.andproger.testtaskaura.domain.usecase

import com.andproger.testtaskaura.domain.model.BootEventSummary
import kotlinx.coroutines.flow.Flow

interface GetBootEventsSummaryUseCase {
    fun getSummary(): Flow<BootEventSummary>
}