package com.andproger.testtaskaura.presentation.notifications

import com.andproger.testtaskaura.R
import com.andproger.testtaskaura.domain.date.AppDateFormatter
import com.andproger.testtaskaura.domain.date.DateFormat
import com.andproger.testtaskaura.domain.model.BootEvent
import com.andproger.testtaskaura.presentation.resources.AppResProvider
import javax.inject.Inject

class BootNotificationMapper @Inject constructor(
    private val resProvider: AppResProvider,
    private val dateFormatter: AppDateFormatter
) : (List<BootEvent>) -> String {

    override fun invoke(bootEvents: List<BootEvent>): String {
        return when {
            bootEvents.isEmpty() -> resProvider.getString(R.string.no_boots_detected)
            bootEvents.size == 1 -> resProvider.getString(
                R.string.the_boot_was_detected,
                dateFormatter.format(DateFormat.ddMMyyyyHHmmss, bootEvents.first().timestamp)
            )
            else -> {
                val lastBootTime = bootEvents[0].timestamp
                val secondLastBootTime = bootEvents[1].timestamp
                val delta = lastBootTime - secondLastBootTime
                resProvider.getString(
                    R.string.last_boots_time_delta,
                    dateFormatter.duration(delta)
                )
            }
        }
    }
}