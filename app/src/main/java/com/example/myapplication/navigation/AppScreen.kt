package com.example.myapplication.navigation

sealed class AppScreen(val route: String){
    object HomeScreen: AppScreen("home")
    object MainScreen : AppScreen("main_screen")
    object EstudianteScreen : AppScreen("estudiante")
    object EspecialistaScreen : AppScreen("especialista")
    object RealizarTest : AppScreen("test")
    object MostrarTestRealizado : AppScreen("realizado")
    object EspecialistaFiltro : AppScreen("EspecialistaFiltro")
    
}
