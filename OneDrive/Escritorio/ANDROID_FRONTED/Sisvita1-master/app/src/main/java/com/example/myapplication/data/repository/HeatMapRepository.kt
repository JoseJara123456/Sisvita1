package com.example.myapplication.data.repository

import com.example.myapplication.data.model.HeatMapResponse
import com.example.myapplication.network.ApiInstance

class  HeatMapRepository {
    private val apiService = ApiInstance.apiInstance

    suspend fun getHeatMapData(): HeatMapResponse {
        return apiService.getHeatMapData()
    }
}
