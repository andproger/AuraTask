package com.andproger.testtaskaura.data.repository

import com.andproger.testtaskaura.data.dao.BootEventsDao
import com.andproger.testtaskaura.data.mapper.mapBootEventToDomain
import com.andproger.testtaskaura.data.mapper.mapBootEventToEntity
import com.andproger.testtaskaura.domain.model.BootEvent
import com.andproger.testtaskaura.domain.repository.BootEventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject


class BootEventRepositoryImpl @Inject constructor(
    private val bootEventsDao: BootEventsDao
) : BootEventRepository {
    override suspend fun saveBootEvent(bootEvent: BootEvent) = withContext(Dispatchers.IO) {
        bootEventsDao.insert(mapBootEventToEntity(bootEvent))
    }

    override suspend fun getBootEvents(): List<BootEvent> = withContext(Dispatchers.IO) {
        bootEventsDao.getAll().map(::mapBootEventToDomain)
    }

    override fun getBootEventsFlow(): Flow<List<BootEvent>> {
        return bootEventsDao.getAllFlow()
            .flowOn(Dispatchers.IO)
            .map { it.map(::mapBootEventToDomain) }
    }
}