package com.andproger.testtaskaura.domain.usecase

import com.andproger.testtaskaura.domain.model.BootEventSummary
import com.andproger.testtaskaura.domain.repository.BootEventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


class GetBootEventsSummaryUseCaseImpl @Inject constructor(
    private val bootEventRepository: BootEventRepository
) : GetBootEventsSummaryUseCase {

    override fun getSummary(): Flow<BootEventSummary> {
        return bootEventRepository.getBootEventsFlow().map { events ->
            val eventsPerDay = events.groupBy { event ->
                toStartOfDay(Date(event.timestamp))
            }.mapValues { entry ->
                entry.value.size
            }

            BootEventSummary(eventsPerDay)
        }
    }

    //TODO could be moved to some util class
    private fun toStartOfDay(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }
}