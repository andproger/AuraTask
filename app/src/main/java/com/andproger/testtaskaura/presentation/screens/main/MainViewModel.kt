package com.andproger.testtaskaura.presentation.screens.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andproger.testtaskaura.data.dao.BootEventsDao
import com.andproger.testtaskaura.data.entity.BootEventEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val dao: BootEventsDao
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dao.getAll().let {
                Log.d("MainViewModel", it.toString())
            }
        }
    }
}