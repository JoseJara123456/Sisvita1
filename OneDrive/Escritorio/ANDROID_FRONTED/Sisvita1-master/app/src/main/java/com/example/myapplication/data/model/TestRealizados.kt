package com.example.myapplication.data.model

// Definición de una clase de datos en Kotlin para manejar las respuestas de los tests realizados
data class TestsRealizadosResponse(
    val data: List<TestReali>,  // Lista de tests realizados, cada elemento es un 'TestReali'
    val message: String,        // Mensaje descriptivo sobre la respuesta
    val status: Int             // Código de estado HTTP para la respuesta
) {
    // Clase de datos interna para representar un test realizado específico
    data class TestReali (
        val fecha_test: String,     // Fecha en que se realizó el test
        val nombre_test: String,    // Nombre del test
        val nombre_usuario: String, // Nombre del usuario que realizó el test
        val puntaje: Int,           // Puntaje obtenido en el test
        val test_id: Int,            // Identificador único del test
        val nivel_ansiedad: String
    )

}
