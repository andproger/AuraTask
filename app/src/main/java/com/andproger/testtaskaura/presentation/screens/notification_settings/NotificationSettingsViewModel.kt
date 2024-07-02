package com.andproger.testtaskaura.presentation.screens.notification_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andproger.testtaskaura.domain.model.BootNotificationParams
import com.andproger.testtaskaura.domain.repository.BootNotificationParamsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationSettingsViewModel @Inject constructor(
    private val bootNotificationParamsRepository: BootNotificationParamsRepository
) : ViewModel() {

    private val _totalDismissalsAllowedState: MutableStateFlow<Int?> = MutableStateFlow(null)
    val totalDismissalsAllowedState: StateFlow<Int?> = _totalDismissalsAllowedState

    private val _intervalBetweenDismissalsMinState: MutableStateFlow<Int?> = MutableStateFlow(null)
    val intervalBetweenDismissalsMinState: StateFlow<Int?> = _intervalBetweenDismissalsMinState

    init {
        viewModelScope.launch {
            bootNotificationParamsRepository.getFlow().collect { params ->
                _totalDismissalsAllowedState.update {
                    params.totalDismissalsAllowed
                }
                _intervalBetweenDismissalsMinState.update {
                    params.intervalBetweenDismissalsMin.toInt()
                }
            }
        }
    }

    fun onTotalDismissalsAllowedChanged(newValue: String) {
        //TODO add validation
        _totalDismissalsAllowedState.update {
            newValue.toIntOrNull()
        }
    }

    fun onIntervalBetweenDismissalsChanged(newValue: String) {
        //TODO add validation
        _intervalBetweenDismissalsMinState.update {
            newValue.toIntOrNull()
        }
    }

    fun saveSettings() {
        viewModelScope.launch {
            val dismissalsAllowed = _totalDismissalsAllowedState.value
            val intervalBetween = _intervalBetweenDismissalsMinState.value
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
}