package com.andproger.testtaskaura.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andproger.testtaskaura.data.entity.BootEventEntity

@Dao
interface BootEventsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bootEventEntity: BootEventEntity)

    @Query("SELECT * FROM BootEventEntity  ORDER BY timestamp DESC")
    suspend fun getAll(): List<BootEventEntity>
}