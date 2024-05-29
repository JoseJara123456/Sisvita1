package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.view.HomeScreen


@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreen.HomeScreen.route){
        composable(route = AppScreen.HomeScreen.route){
            HomeScreen(navController)
        }

        /*composable(route = AppScreen.informationScreen.route +"/{body}/{body1}"){
            val body = it.arguments?.getString("body") ?: "0"
            val body1 = it.arguments?.getString("body1") ?: "0"
            InformationScreen(navController,body,body1)
        }*/
    }
}