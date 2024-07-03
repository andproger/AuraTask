package com.andproger.testtaskaura.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andproger.testtaskaura.domain.usecase.GetBootEventsSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBootEventsSummaryUseCase: GetBootEventsSummaryUseCase,
    private val bootEventSummaryMapper: BootEventSummaryMapper
) : ViewModel() {

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState.Empty)
    val state: StateFlow<MainState> = _state

    init {
        viewModelScope.launch {
            getBootEventsSummaryUseCase.getSummary().collect { summary ->
                val text = bootEventSummaryMapper(summary)
                _state.update { it.copy(summaryText = text) }
            }
        }
    }
}