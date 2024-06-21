package com.example.myapplication.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.TestsRealizadosResponse
import com.example.myapplication.data.repository.TestRealizadosRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class TestRealizadosViewModel : ViewModel() {

    var testsYRespuestas by mutableStateOf<List<TestsRealizadosResponse.TestReali>?>(null)
    var errorMessage by mutableStateOf("")
    private val testRealizadosRepository = TestRealizadosRepository()

    fun obtenerTestsRealizados() {
        viewModelScope.launch {
            try {
                val response = testRealizadosRepository.obtenerTestsRealizados()
                testsYRespuestas = response.data
            } catch (e: HttpException) {
                errorMessage = e.message ?: "An error occurred"
            }
        }
    }
    fun filtrarTests(fechaTest: String, tipoTest: String, puntaje: String) {
        viewModelScope.launch {
            try {
                val response = testRealizadosRepository.obtenerTestsRealizados()
                if (response.status == 200) {
                    testsYRespuestas = response.data
                } else {
                    errorMessage = response.message
                }
            } catch (e: IOException) {
                errorMessage = "Error de red. Por favor, inténtalo de nuevo."
            } catch (e: HttpException) {
                errorMessage = "Error al obtener datos del servidor."
            }
        }
    }
}
