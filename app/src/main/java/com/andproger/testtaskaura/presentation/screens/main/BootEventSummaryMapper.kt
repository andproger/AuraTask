package com.andproger.testtaskaura.presentation.screens.main

import com.andproger.testtaskaura.R
import com.andproger.testtaskaura.domain.date.AppDateFormatter
import com.andproger.testtaskaura.domain.date.DateFormat
import com.andproger.testtaskaura.domain.model.BootEventSummary
import com.andproger.testtaskaura.presentation.resources.AppResProvider
import javax.inject.Inject

class BootEventSummaryMapper @Inject constructor(
    private val appResProvider: AppResProvider,
    private val dateFormatter: AppDateFormatter
) : (BootEventSummary) -> String {

    override fun invoke(summary: BootEventSummary): String {
        if (summary.eventsPerDay.isEmpty()) return appResProvider.getString(R.string.no_boots_detected)
        return summary.eventsPerDay.entries.joinToString("\n") {
            "${dateFormatter.format(DateFormat.ddMMyyyy, it.key)} - ${it.value}"
        }
    }
}