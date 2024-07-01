package com.andproger.testtaskaura.presentation.screens.main

data class MainState(
    val summaryText: String,
    val totalDismissalsAllowed: Int? = null,
    val intervalBetweenDismissals: Int? = null
) {
    companion object {
        val Empty = MainState(
            summaryText = "",
            totalDismissalsAllowed = null,
            intervalBetweenDismissals = null
        )
    }
}