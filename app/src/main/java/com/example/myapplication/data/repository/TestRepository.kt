package com.example.myapplication.data.repository

import com.example.myapplication.data.model.LoginRequest
import com.example.myapplication.data.model.LoginResponse
import com.example.myapplication.data.model.TestsYPreguntasResponse
import com.example.myapplication.network.ApiInstance

class TestRepository {
    private val apiService = ApiInstance.apiInstance

    suspend fun obtenerTestsYPreguntasUsuario(): TestsYPreguntasResponse {
        return apiService.obtenerTestsYPreguntasUsuario()
    }
}