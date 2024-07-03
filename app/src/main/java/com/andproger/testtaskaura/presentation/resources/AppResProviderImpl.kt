package com.andproger.testtaskaura.presentation.resources

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppResProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : AppResProvider {

    override fun getString(id: Int) =
        context.getString(id)

    override fun getString(
        id: Int,
        vararg params: Any,
    ) = context.getString(id, *params)
}