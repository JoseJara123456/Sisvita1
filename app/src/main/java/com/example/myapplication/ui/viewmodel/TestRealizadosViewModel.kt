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

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.SelectedTestData
import com.example.myapplication.data.model.TestsRealizadosResponse
import com.example.myapplication.data.repository.TestRealizadosRepository
import com.example.myapplication.data.model.TestData
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class TestRealizadosViewModel : ViewModel() {

    var testsYRespuestas by mutableStateOf<List<TestsRealizadosResponse.TestReali>?>(null)  // Lista de tests realizados
    var errorMessage by mutableStateOf("")  // Mensaje de error para mostrar en la UI
    private val testRealizadosRepository = TestRealizadosRepository()

    private var selectedTestData: TestData? = null

    fun saveSelectedTestData(testData: TestData) {
        SelectedTestData.saveTestData(testData)
    }

    fun getSelectedTestData(): TestData? {
        Log.d("TestRealizadosViewModel", "Returning Selected Test Data: $selectedTestData")
        return selectedTestData
    }


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
