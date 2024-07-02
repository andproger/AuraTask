package com.andproger.testtaskaura.presentation.screens.main

data class MainState(
    val summaryText: String
) {
    companion object {
        val Empty = MainState(
            summaryText = ""
        )
    }
}