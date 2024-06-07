package com.example.myapplication.data.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val usuarioId: String,
    val nombre: String,
    val email: String,
    val rol: String,
)