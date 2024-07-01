package com.andproger.testtaskaura.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BootEventEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val timestamp: Long
)