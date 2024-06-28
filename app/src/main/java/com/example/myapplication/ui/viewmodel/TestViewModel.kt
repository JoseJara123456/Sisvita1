package com.example.myapplication.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.TestRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import com.example.myapplication.data.model.TestsYPreguntasResponse
import com.example.myapplication.data.model.EnviarRespuestasRequest
import com.example.myapplication.data.model.RespuestaRequest

class TestViewModel : ViewModel() {
    var testsYPreguntas by mutableStateOf<List<TestsYPreguntasResponse.tipoTest>?>(null)
    var errorMessage by mutableStateOf("")
    private val testRepository = TestRepository()
    var nivel by mutableStateOf<String?>(null)

    fun obtenerTestsYPreguntasUsuario() {
        viewModelScope.launch {
            try {
                val response = testRepository.obtenerTestsYPreguntasUsuario()
                testsYPreguntas = response.data
            } catch (e: HttpException) {
                errorMessage = e.message ?: "An error occurred"
            }
        }
    }


    fun enviarRespuestas(usuarioId: Int, respuestas: List<RespuestaRequest>) {
        viewModelScope.launch {
            try {
                val request = EnviarRespuestasRequest(usuarioId, respuestas)
                val response = testRepository.enviarRespuestas(request)
                nivel=response.nivel_ansiedad
                Log.d("TestViewModel", "Respuestas enviadas correctamente: $response")
            } catch (e: HttpException) {
                errorMessage = "Error al enviar respuestas: ${e.message ?: "Unknown error"}"
                Log.e("TestViewModel", errorMessage, e)
            } catch (e: Exception) {
                errorMessage =
                    "Error inesperado al enviar respuestas: ${e.message ?: "Unknown error"}"
                Log.e("TestViewModel", errorMessage, e)
            }
        }
    }
}
