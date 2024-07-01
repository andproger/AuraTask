package com.andproger.testtaskaura.data.mapper

import com.andproger.testtaskaura.data.entity.BootEventEntity
import com.andproger.testtaskaura.domain.model.BootEvent

fun mapBootEventToDomain(bootEventEntity: BootEventEntity) = with(bootEventEntity) {
    BootEvent(timestamp)
}

fun mapBootEventToEntity(bootEventDomain: BootEvent) = with(bootEventDomain) {
    BootEventEntity(timestamp)
}