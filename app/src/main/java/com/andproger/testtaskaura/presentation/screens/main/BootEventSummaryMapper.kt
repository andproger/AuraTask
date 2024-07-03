package com.andproger.testtaskaura.presentation.screens.main

import com.andproger.testtaskaura.R
import com.andproger.testtaskaura.domain.model.BootEventSummary
import com.andproger.testtaskaura.presentation.resources.AppResProvider
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class BootEventSummaryMapper @Inject constructor(
    private val appResProvider: AppResProvider
) : (BootEventSummary) -> String {

    //TODO move date formatting to date formatter utils
    override fun invoke(summary: BootEventSummary): String {
        if (summary.eventsPerDay.isEmpty()) return appResProvider.getString(R.string.no_boots_detected)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return summary.eventsPerDay.entries.joinToString("\n") {
            "${dateFormat.format(it.key)} - ${it.value}"
        }
    }
}