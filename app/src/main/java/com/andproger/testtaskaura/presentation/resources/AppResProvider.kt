package com.andproger.testtaskaura.presentation.resources


import androidx.annotation.StringRes

interface AppResProvider {

    fun getString(@StringRes id: Int): String

    fun getString(
        @StringRes id: Int,
        vararg params: Any,
    ): String
}
