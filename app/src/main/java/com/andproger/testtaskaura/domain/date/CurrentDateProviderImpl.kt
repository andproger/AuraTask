package com.andproger.testtaskaura.domain.date

import java.util.Date
import javax.inject.Inject

class CurrentDateProviderImpl @Inject constructor() : CurrentDateProvider {
    override fun provideCurrentTimestamp() =
        Date().time
}