package com.andproger.testtaskaura.data.repository

import android.content.Context
import com.andproger.testtaskaura.data.dao.BootEventsDao
import com.andproger.testtaskaura.data.mapper.mapBootEventToDomain
import com.andproger.testtaskaura.data.mapper.mapBootEventToEntity
import com.andproger.testtaskaura.domain.model.BootEvent
import com.andproger.testtaskaura.domain.repository.BootEventRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun getBootEventsFlow(): Flow<List<BootEvent>> {
        return bootEventsDao.getAllFlow().map { it.map(::mapBootEventToDomain) }
    }
}