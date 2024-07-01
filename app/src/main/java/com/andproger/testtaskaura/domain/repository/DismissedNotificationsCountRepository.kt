package com.andproger.testtaskaura.domain.repository

interface DismissedNotificationsCountRepository {
    suspend fun increment(): Int

    suspend fun get(): Int

    suspend fun reset()
}