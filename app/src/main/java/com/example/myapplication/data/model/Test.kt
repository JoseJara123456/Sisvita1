package com.example.myapplication.data.model

data class TestsYPreguntasResponse(
    val data: List<tipoTest>,
    val message: String,
    val status: Int

) {
    data class tipoTest(
        val descripcion: String,
        val nombre: String,
        val preguntas: List<Pregunta>,
        val tipotest_id: Int

    )

    data class Pregunta(
        val contenido: String,
        val preguntaid: Int,
        val tipotest_id: Int

    )
}