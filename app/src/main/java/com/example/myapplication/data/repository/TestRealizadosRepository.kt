package com.example.myapplication.data.repository

// Importación de las clases necesarias desde otros paquetes dentro de la aplicación
import com.example.myapplication.data.model.TestsRealizadosResponse
import com.example.myapplication.network.ApiInstance

// Definición de una clase que actúa como repositorio para la funcionalidad de tests realizados
class TestRealizadosRepository {
    // Inicialización de la variable 'apiService' que contiene la instancia de la API para hacer llamadas de red
    private val apiService = ApiInstance.apiInstance

    // Función suspendida que se usa con corutinas en Kotlin para manejar operaciones asíncronas
    suspend fun obtenerTestsRealizados(): TestsRealizadosResponse {
        // Llamada a la función 'obtenerTestsRealizados' de la API y devolución del resultado
        return apiService.obtenerTestsRealizados()
    }
}
