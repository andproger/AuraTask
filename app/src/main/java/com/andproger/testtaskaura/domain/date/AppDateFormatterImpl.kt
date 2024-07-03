package com.andproger.testtaskaura.domain.date

import com.andproger.testtaskaura.R
import com.andproger.testtaskaura.presentation.resources.AppResProvider
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

internal class AppDateFormatterImpl @Inject constructor(
    private val resProvider: AppResProvider
) : AppDateFormatter {

    override fun format(dateFormat: DateFormat, date: Date): String =
        SimpleDateFormat(dateFormat.format, Locale.getDefault()).format(date)

    override fun format(dateFormat: DateFormat, timestamp: Long): String {
        return format(dateFormat, Date(timestamp))
    }

    override fun duration(timestamp: Long): String {
        val seconds = timestamp / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        return resProvider.getString(R.string.time_duration, hours.toString(), (minutes % 60).toString())
    }
}
