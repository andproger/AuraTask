package com.andproger.testtaskaura.presentation.screens.main

data class MainState(
    val summaryText: String,
    val totalDismissalsAllowed: Int? = null,
    val intervalBetweenDismissals: Int? = null
) {
    companion object {
        val Empty = MainState(
            //TODO choose empty state
            summaryText = "Empty summary",
            totalDismissalsAllowed = null,
            intervalBetweenDismissals = null
        )
    }
}