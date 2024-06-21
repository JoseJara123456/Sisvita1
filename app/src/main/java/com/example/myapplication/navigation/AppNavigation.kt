package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.view.EspecialistaScreen
import com.example.myapplication.ui.view.EstudianteScreen
import com.example.myapplication.ui.view.HomeScreen
import com.example.myapplication.ui.view.MainScreen
import com.example.myapplication.ui.view.RealizarTest
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapplication.ui.view.MostrarTestRealizado
import com.example.myapplication.ui.viewmodel.TestViewModel
import com.example.myapplication.data.model.TestsYPreguntasResponse
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
        composable(AppScreen.RealizarTest.route) {
            RealizarTest(navController = navController)
        }

        composable(AppScreen.MostrarTestRealizado.route) {
            MostrarTestRealizado(navController = navController)
        }



}}