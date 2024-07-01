package com.andproger.testtaskaura.domain.model

data class BootNotificationParams(
    val totalDismissalsAllowed: Int,
    val intervalBetweenDismissalsMin: Long
) {
    companion object {
        private const val DISMISS_LIMIT_COUNT = 5
        private const val INTERVAL_PER_DISMISSAL_MIN = 20L

        val default = BootNotificationParams(
            DISMISS_LIMIT_COUNT,
            INTERVAL_PER_DISMISSAL_MIN
        )
    }
}