package com.example.myapplication.data.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginData(
    val usuarioId: Int,
    val nombre: String,
    val email: String,
    val rol: String
)

data class LoginResponse(
    val data: LoginData,
    val message: String,
    val status: Int
)
