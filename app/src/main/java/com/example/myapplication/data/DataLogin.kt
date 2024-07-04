package com.example.myapplication.data


object UserSession {
    var userRole: String? = null
    var idUsuario: String? = null
    var nombre: String? = null

    fun saveUserData(rol: String, usuarioId: String, nombreUsuario: String) {
        userRole = rol
        idUsuario = usuarioId
        nombre = nombreUsuario
    }

    fun clearUserData() {
        userRole = null
        idUsuario = null
        nombre = null
    }
}