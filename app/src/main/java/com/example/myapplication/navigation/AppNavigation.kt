package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.view.HomeScreen
import com.example.myapplication.ui.view.MainScreen


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
    }
}