package com.example.myapplication.navigation

// Importaciones necesarias para utilizar Jetpack Compose y Navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.view.EspecialistaScreen
import com.example.myapplication.ui.view.EspecialistaFiltro
import com.example.myapplication.ui.view.EstudianteScreen
import com.example.myapplication.ui.view.HomeScreen
import com.example.myapplication.ui.view.MainScreen
import com.example.myapplication.ui.view.RealizarTest
import com.example.myapplication.ui.view.*
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.myapplication.ui.viewmodel.TestViewModel
import com.example.myapplication.data.model.TestsYPreguntasResponse

// Función marcada con @Composable que permite usar Compose para definir la UI
@Composable
fun AppNavigation() {
    // Crear y recordar un controlador de navegación para gestionar la navegación en la aplicación
    val navController = rememberNavController()

    // Definir el host de navegación que controla las rutas dentro de la aplicación
    NavHost(navController = navController, startDestination = AppScreen.HomeScreen.route) {
        // Ruta composable para la pantalla principal
        composable(route = AppScreen.HomeScreen.route) {
            HomeScreen(navController)
        }

        // Ruta composable para la pantalla principal
        composable(AppScreen.MainScreen.route) {
            MainScreen(navController = navController)
        }

        // Ruta composable para la pantalla del estudiante
        composable(AppScreen.EstudianteScreen.route) {
            EstudianteScreen(navController = navController)
        }

        // Ruta composable para la pantalla del especialista
        composable(AppScreen.EspecialistaScreen.route) {
            EspecialistaScreen(navController = navController)
        }

        // Ruta composable para realizar un test, incluye pasar un parámetro 'usuarioId'
        composable(AppScreen.RealizarTest.route + "/{usuarioId}") { backStackEntry ->
            // Recuperar el 'usuarioId' pasado como parámetro y convertirlo a Int, si es nulo se usa 0
            val usuarioId = backStackEntry.arguments?.getString("usuarioId")?.toIntOrNull() ?: 0
            RealizarTest(navController, usuarioId)
        }

        // Ruta composable para mostrar los tests realizados
        composable(AppScreen.MostrarTestRealizado.route) {
            MostrarTestRealizado(navController = navController)
        }

        composable(route = AppScreen.EspecialistaFiltro.route) {
            EspecialistaFiltro(navController = navController)
        }
    }
}
