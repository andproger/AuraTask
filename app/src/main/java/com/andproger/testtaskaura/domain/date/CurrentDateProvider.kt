package com.andproger.testtaskaura.domain.date

interface CurrentDateProvider {
    fun provideCurrentTimestamp(): Long
}