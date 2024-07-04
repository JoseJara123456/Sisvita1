package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.view.EspecialistaFiltro
import com.example.myapplication.ui.view.EspecialistaScreen
import com.example.myapplication.ui.view.EstudianteScreen
import com.example.myapplication.ui.view.HeatmapScreen
import com.example.myapplication.ui.view.HomeScreen
import com.example.myapplication.ui.view.MainScreen
import com.example.myapplication.ui.view.MostrarTestRealizado
import com.example.myapplication.ui.view.RealizarTest
//import com.example.myapplication.ui.view.detalles

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreen.HomeScreen.route){
        composable(route = AppScreen.HomeScreen.route){
            HomeScreen(navController)
        }

        composable(AppScreen.MainScreen.route) {
            MainScreen(navController = navController)
        }

        composable(AppScreen.EstudianteScreen.route) {
            EstudianteScreen(navController = navController)
        }
        composable(AppScreen.EspecialistaScreen.route) {
            EspecialistaScreen(navController = navController)
        }
        composable(AppScreen.RealizarTest.route + "/{usuarioId}") { backStackEntry ->
            val usuarioId = backStackEntry.arguments?.getString("usuarioId")?.toIntOrNull() ?: 0
            RealizarTest(navController, usuarioId)
        }

        composable(AppScreen.MostrarTestRealizado.route) {
            MostrarTestRealizado(navController = navController)
        }

        composable(route = AppScreen.EspecialistaFiltro.route) {
            EspecialistaFiltro(navController = navController)
        }
        composable(route = AppScreen.HeatmapScreen.route) {
            HeatmapScreen(navController = navController)
        }
        /*composable(route = detalles.route) {
            detalles(navController = navController)
        }*/
}}
