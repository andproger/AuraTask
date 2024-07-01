package com.andproger.testtaskaura.domain.usecase

import com.andproger.testtaskaura.domain.model.BootEventSummary
import com.andproger.testtaskaura.domain.repository.BootEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class GetBootEventsSummaryUseCaseImpl @Inject constructor(
    private val bootEventRepository: BootEventRepository
) : GetBootEventsSummaryUseCase {
    override fun getSummary(): Flow<BootEventSummary> {
        return bootEventRepository.getBootEventsFlow().map { events ->
            val eventsPerDay = events.groupBy { event ->
                val millisInDay = TimeUnit.DAYS.toMillis(1)
                Date(event.timestamp / millisInDay * millisInDay)
            }.mapValues { entry ->
                entry.value.size
            }

            BootEventSummary(eventsPerDay)
        }
    }
}