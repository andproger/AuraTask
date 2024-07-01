package com.andproger.testtaskaura.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BootEventEntity(
    val timestamp: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}