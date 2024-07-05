package com.example.myapplication.data.repository


import com.example.myapplication.data.model.DiagnosticosRequest
import com.example.myapplication.data.model.EnviarRespuestasRequest
import com.example.myapplication.data.model.RespuestaResponse
import com.example.myapplication.data.model.DiagnosticoResponse
import com.example.myapplication.network.ApiInstance
import retrofit2.HttpException

class DiagnosticoRepository {
    private val apiService = ApiInstance.apiInstance

    suspend fun enviarDatosDiagnostico(request: DiagnosticosRequest): DiagnosticoResponse {
        try {
            val response = apiService.enviarDatosDiagnostico(request)
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