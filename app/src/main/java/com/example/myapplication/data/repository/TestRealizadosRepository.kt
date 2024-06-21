package com.example.myapplication.data.repository

import com.example.myapplication.data.model.TestsRealizadosResponse
import com.example.myapplication.network.ApiInstance

class TestRealizadosRepository {
    private val apiService = ApiInstance.apiInstance

    suspend fun obtenerTestsRealizados(): TestsRealizadosResponse {
        return apiService.obtenerTestsRealizados()
    }
}