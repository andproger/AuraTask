package com.andproger.testtaskaura.domain.date

import java.util.Date

interface AppDateFormatter {
    fun format(dateFormat: DateFormat, date: Date): String
    fun format(dateFormat: DateFormat, timestamp: Long): String
    fun duration(timestamp: Long): String
}
