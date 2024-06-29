package com.example.myapplication.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.HeatMapResponse
import com.example.myapplication.data.repository.HeatMapRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HeatMapViewModel : ViewModel() {
    var heatMapData by mutableStateOf<List<HeatMapResponse.HeatMapData>?>(null)
    var errorMessage by mutableStateOf("")  // Mensaje de error para mostrar en la UI

    private val heatMapRepository = HeatMapRepository()

    fun getDataHeatMap() {
        viewModelScope.launch {
            try {
                val response = heatMapRepository.getHeatMapData()
                heatMapData = response.data
            } catch (e: HttpException) {
                errorMessage = e.message ?: "An error occurred"
            }
        }
    }
}








