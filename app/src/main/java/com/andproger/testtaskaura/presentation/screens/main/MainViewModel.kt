package com.andproger.testtaskaura.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andproger.testtaskaura.domain.model.BootEventSummary
import com.andproger.testtaskaura.domain.model.BootNotificationParams
import com.andproger.testtaskaura.domain.repository.BootNotificationParamsRepository
import com.andproger.testtaskaura.domain.usecase.GetBootEventsSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBootEventsSummaryUseCase: GetBootEventsSummaryUseCase,
    private val bootNotificationParamsRepository: BootNotificationParamsRepository
) : ViewModel() {

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState.Empty)
    val state: StateFlow<MainState> = _state

    init {
        viewModelScope.launch {
            getBootEventsSummaryUseCase.getSummary().collect { summary ->
                val text = getSummaryString(summary)
                _state.update { it.copy(summaryText = text) }
            }
        }
        viewModelScope.launch {
            bootNotificationParamsRepository.getFlow().collect { params ->
                _state.update {
                    it.copy(
                        totalDismissalsAllowed = params.totalDismissalsAllowed,
                        intervalBetweenDismissals = params.intervalBetweenDismissalsMin.toInt()
                    )
                }
            }
        }
    }

    fun onTotalDismissalsAllowedChanged(newValue: String) {
        //TODO add validation
        _state.update {
            it.copy(totalDismissalsAllowed = newValue.toIntOrNull())
        }
    }

    fun onIntervalBetweenDismissalsChanged(newValue: String) {
        //TODO add validation
        _state.update {
            it.copy(intervalBetweenDismissals = newValue.toIntOrNull())
        }
    }

    fun saveSettings() {
        viewModelScope.launch {
            val dismissalsAllowed = _state.value.totalDismissalsAllowed
            val intervalBetween = _state.value.intervalBetweenDismissals
            //TODO add validation
            if (dismissalsAllowed != null && intervalBetween != null) {
                bootNotificationParamsRepository.save(
                    BootNotificationParams(
                        totalDismissalsAllowed = dismissalsAllowed,
                        intervalBetweenDismissalsMin = intervalBetween.toLong()
                    )
                )
            }
        }
    }

    //TODO string resources
    //TODO move date formatting to date formatter utils
    private fun getSummaryString(summary: BootEventSummary): String {
        if (summary.eventsPerDay.isEmpty()) return "No boots detected"
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return summary.eventsPerDay.entries.joinToString("\n") {
            "${dateFormat.format(it.key)} - ${it.value}"
        }
    }
}