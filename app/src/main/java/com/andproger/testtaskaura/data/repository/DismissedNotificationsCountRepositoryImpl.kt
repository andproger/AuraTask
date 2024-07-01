package com.andproger.testtaskaura.data.repository

import android.content.Context
import com.andproger.testtaskaura.domain.repository.DismissedNotificationsCountRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class DismissedNotificationsCountRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DismissedNotificationsCountRepository {
    private val prefs = context.getSharedPreferences(DISMISSED_COUNT_PREFS, Context.MODE_PRIVATE)

    override suspend fun increment(): Int {
        val currentCount = prefs.getInt(DISMISSED_COUNT_KEY, 0)
        val newCount = currentCount + 1
        prefs.edit().putInt(DISMISSED_COUNT_KEY, newCount).apply()
        return newCount
    }

    override suspend fun get(): Int {
        return prefs.getInt(DISMISSED_COUNT_KEY, 0)
    }

    override suspend fun reset() {
        prefs.edit().putInt(DISMISSED_COUNT_KEY, 0).apply()
    }

    companion object {
        const val DISMISSED_COUNT_PREFS = "DISMISSED_COUNT_PREFS"
        const val DISMISSED_COUNT_KEY = "TOTAL_DISMISSALS_ALLOWED"
    }
}