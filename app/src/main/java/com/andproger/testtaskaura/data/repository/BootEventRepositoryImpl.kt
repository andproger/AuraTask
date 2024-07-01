package com.andproger.testtaskaura.data.repository

import android.content.Context
import android.preference.PreferenceManager
import com.andproger.testtaskaura.data.dao.BootEventsDao
import com.andproger.testtaskaura.data.mapper.mapBootEventToDomain
import com.andproger.testtaskaura.data.mapper.mapBootEventToEntity
import com.andproger.testtaskaura.domain.model.BootEvent
import com.andproger.testtaskaura.domain.repository.BootEventRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class BootEventRepositoryImpl @Inject constructor(
    private val bootEventsDao: BootEventsDao,
    @ApplicationContext private val context: Context
) : BootEventRepository {
    override suspend fun saveBootEvent(bootEvent: BootEvent) {
        bootEventsDao.insert(mapBootEventToEntity(bootEvent))
    }

    override suspend fun getBootEvents(): List<BootEvent> {
        return bootEventsDao.getAll().map(::mapBootEventToDomain)
    }

    override suspend fun incrementDismissCount(): Int {
        //TODO: Use DataStore instead of SharedPreferences
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val currentCount = prefs.getInt(DISMISS_COUNT_KEY, 0)
        val newCount = currentCount + 1
        prefs.edit().putInt(DISMISS_COUNT_KEY, newCount).apply()
        return newCount
    }

    suspend fun resetDismissCount() {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit().putInt(DISMISS_COUNT_KEY, 0).apply()
    }

    companion object {
        const val DISMISS_COUNT_KEY = "dismiss_count"
    }
}