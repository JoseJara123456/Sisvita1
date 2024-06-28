package com.example.myapplication.data.model

data class RespuestaResponse(
    val message: String,
    val status: Int,
    val nivel_ansiedad: String
)
data class EnviarRespuestasRequest(
    val usuarioId: Int,
    val respuestas: List<RespuestaRequest>
)
data class RespuestaRequest(
    val preguntaId: Int,
    val opcionId: Int
)
