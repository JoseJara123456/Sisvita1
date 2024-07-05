package com.example.myapplication.data.model

data class DiagnosticosRequest(
    val idUsuario: Int,
    val testId: String,
    val tratamiento : String,
    val diagnosticoNivelAnsiedad: String,
    val fundamentacionCientifica: String,
    val observaciones: String
)
data class DiagnosticoResponse(
    val message: String,
    val status: Int,
)