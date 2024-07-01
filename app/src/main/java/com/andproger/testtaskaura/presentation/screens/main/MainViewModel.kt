package com.andproger.testtaskaura.presentation.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andproger.testtaskaura.data.dao.BootEventsDao
import com.andproger.testtaskaura.data.entity.BootEventEntity
import com.andproger.testtaskaura.domain.model.BootEventSummary
import com.andproger.testtaskaura.domain.usecase.GetBootEventsSummaryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBootEventsSummaryUseCase: GetBootEventsSummaryUseCase
    //TODO add usecase for changing notification params
) : ViewModel() {

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState.Empty)
    val state: StateFlow<MainState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getBootEventsSummaryUseCase.getSummary().collect { summary ->
                val text = getSummaryString(summary)
                _state.value = MainState(text)
            }
        }
    }

    //TODO string resources
    //TODO use defined date formatter
    private fun getSummaryString(summary: BootEventSummary): String {
        if (summary.eventsPerDay.isEmpty()) return "No boots detected"
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return summary.eventsPerDay.entries.joinToString("\n") {
            "${dateFormat.format(it.key)} - ${it.value}"
        }
    }
}