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

// Clase que extiende de ViewModel, proporcionando un ámbito de datos persistente a lo largo del ciclo de vida de las vistas asociadas
class TestRealizadosViewModel : ViewModel() {

    // Propiedades observables que se actualizarán en la UI cuando cambien
    var testsYRespuestas by mutableStateOf<List<TestsRealizadosResponse.TestReali>?>(null)  // Lista de tests realizados
    var errorMessage by mutableStateOf("")  // Mensaje de error para mostrar en la UI

    // Instancia del repositorio que contiene métodos para obtener datos de la API
    private val testRealizadosRepository = TestRealizadosRepository()

    // Función para obtener los tests realizados. Se lanza dentro del ámbito del ViewModel para manejar la corutina
    fun obtenerTestsRealizados() {
        viewModelScope.launch {
            try {
                // Intentar obtener los datos desde el repositorio
                val response = testRealizadosRepository.obtenerTestsRealizados()
                // Si es exitoso, actualizar la lista de tests realizados
                testsYRespuestas = response.data
            } catch (e: HttpException) {
                // En caso de error HTTP, capturar la excepción y actualizar el mensaje de error
                errorMessage = e.message ?: "An error occurred"
            }
        }
    }
}
