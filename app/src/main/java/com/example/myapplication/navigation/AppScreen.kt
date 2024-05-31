package com.example.myapplication.navigation

sealed class AppScreen(val route: String){
    object HomeScreen: AppScreen("home")
    object MainScreen : AppScreen("main_screen")

}
