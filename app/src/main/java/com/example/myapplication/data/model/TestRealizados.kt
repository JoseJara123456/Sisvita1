package com.example.myapplication.data.model

data class TestsRealizadosResponse(
    val data: List<TestReali>,
    val message: String,
    val status: Int
) {
    data class TestReali (
        val fecha_test: String,
        val nombre_test: String,
        val nombre_usuario: String,
        val puntaje: Int,
        val test_id: Int
    )

}
