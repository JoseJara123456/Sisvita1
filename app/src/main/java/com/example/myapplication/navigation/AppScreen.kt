package com.example.myapplication.navigation

sealed class AppScreen(val route: String) {

    object HomeScreen : AppScreen("home") // Pantalla de inicio
    object MainScreen : AppScreen("main_screen") // Pantalla principal
    object EstudianteScreen : AppScreen("estudiante") // Pantalla para estudiantes
    object EspecialistaScreen : AppScreen("especialista") // Pantalla para especialistas
    object RealizarTest : AppScreen("test") // Pantalla para realizar tests
    object MostrarTestRealizado : AppScreen("realizado") // Pantalla para mostrar tests realizados
}
