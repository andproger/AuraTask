package com.andproger.testtaskaura.data.repository

import android.content.Context
import com.andproger.testtaskaura.domain.model.BootNotificationParams
import com.andproger.testtaskaura.domain.repository.BootNotificationParamsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


class BootNotificationParamsRepositoryImpl(
    @ApplicationContext private val context: Context,
    private val default: BootNotificationParams
) : BootNotificationParamsRepository {

    private val prefs = context.getSharedPreferences(BOOT_NOTIFICATION_PARAMS_PREFS, Context.MODE_PRIVATE)
    private val stateFlow = MutableStateFlow(get())

    override suspend fun save(params: BootNotificationParams) {
        prefs.edit()
            .putInt(TOTAL_DISMISSALS_ALLOWED_KEY, params.totalDismissalsAllowed)
            .putLong(INTERVAL_BETWEEN_DISMISSALS_MIN_KEY, params.intervalBetweenDismissalsMin)
            .apply()
        stateFlow.emit(get())
    }

    override fun get(): BootNotificationParams {
        return BootNotificationParams(
            totalDismissalsAllowed = prefs.getInt(TOTAL_DISMISSALS_ALLOWED_KEY, default.totalDismissalsAllowed),
            intervalBetweenDismissalsMin = prefs.getLong(INTERVAL_BETWEEN_DISMISSALS_MIN_KEY, default.intervalBetweenDismissalsMin)
        )
    }

    override fun getFlow(): Flow<BootNotificationParams> {
        return stateFlow
    }

    companion object {
        const val BOOT_NOTIFICATION_PARAMS_PREFS = "BOOT_NOTIFICATION_PARAMS_PREFS"
        const val TOTAL_DISMISSALS_ALLOWED_KEY = "TOTAL_DISMISSALS_ALLOWED"
        const val INTERVAL_BETWEEN_DISMISSALS_MIN_KEY = "INTERVAL_BETWEEN_DISMISSALS_MIN"
    }
}