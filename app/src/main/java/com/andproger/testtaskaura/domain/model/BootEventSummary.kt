package com.andproger.testtaskaura.domain.model

import java.util.Date

data class BootEventSummary(
    val eventsPerDay: Map<Date, Int>
)