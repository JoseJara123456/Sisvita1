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
import com.example.myapplication.data.model.DiagnosticosRequest
import com.example.myapplication.data.model.RespuestaRequest
import com.example.myapplication.data.repository.DiagnosticoRepository

class DiagnosticoViewModel : ViewModel() {
    var testsYPreguntas by mutableStateOf<List<TestsYPreguntasResponse.tipoTest>?>(null)
    var errorMessage by mutableStateOf("")
    private val diagnosticoRepository = DiagnosticoRepository()
    var nivel by mutableStateOf<String?>(null)

    fun enviarDatosDiagnostico(idUsuario: Int, testId: String, tratamiento: String, diagnosticoNivelAnsiedad: String, fundamentacionCientifica: String, observaciones: String) {
        viewModelScope.launch {
            try {
                val request = DiagnosticosRequest(idUsuario, testId, tratamiento, diagnosticoNivelAnsiedad, fundamentacionCientifica, observaciones)
                val response = diagnosticoRepository.enviarDatosDiagnostico(request)
                Log.d("DiagnosticoViewModel", "Respuestas enviadas correctamente: $response")
            } catch (e: HttpException) {
                errorMessage = "Error al enviar respuestas: ${e.message ?: "Unknown error"}"
                Log.e("DiagnosticoViewModell", errorMessage, e)
            } catch (e: Exception) {
                errorMessage =
                    "Error inesperado al enviar respuestas: ${e.message ?: "Unknown error"}"
                Log.e("DiagnosticoViewModell", errorMessage, e)
            }
        }
    }
}
