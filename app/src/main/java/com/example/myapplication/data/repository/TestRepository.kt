package com.example.myapplication.data.repository


import com.example.myapplication.data.model.EnviarRespuestasRequest
import com.example.myapplication.data.model.RespuestaResponse
import com.example.myapplication.data.model.TestsYPreguntasResponse
import com.example.myapplication.network.ApiInstance
import retrofit2.HttpException

class TestRepository {
    private val apiService = ApiInstance.apiInstance

    suspend fun obtenerTestsYPreguntasUsuario(): TestsYPreguntasResponse {
        return apiService.obtenerTestsYPreguntasUsuario()
    }

    suspend fun enviarRespuestas(request: EnviarRespuestasRequest): RespuestaResponse {
        try {
            val response = apiService.enviarRespuestas(request)
            return response
        } catch (e: HttpException) {
            // Manejo de errores
            throw e
        } catch (e: Exception) {
            // Otros errores
            throw e
        }
    }
}