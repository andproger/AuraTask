package com.andproger.testtaskaura.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andproger.testtaskaura.data.dao.BootEventsDao
import com.andproger.testtaskaura.data.entity.BootEventEntity

@Database(
    version = 1,
    entities = [BootEventEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getBootEventsDao(): BootEventsDao

    companion object {
        const val DATABASE_NAME = "app_database.db"
    }
}