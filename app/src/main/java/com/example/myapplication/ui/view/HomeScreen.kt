@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.AppScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewmodel.LoginViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: LoginViewModel = viewModel()
    // Observar el estado de loginResponse y errorMessage desde el ViewModel
    val loginResponse by viewModel.loginResponse
    val errorMessage by viewModel.errorMessage
    val userRole by viewModel.userRole

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Login",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF085394))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido a Sisvita",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Campo de texto para el correo
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                label = { Text("Correo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            // Campo de texto para la contraseña
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )
            // Botón de login
            Button(
                onClick = {
                    if (viewModel.email.isNotEmpty() && viewModel.password.isNotEmpty()) {
                        viewModel.login() // Llamar al método de login en el ViewModel
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF085394)),
                enabled = viewModel.email.isNotEmpty() && viewModel.password.isNotEmpty() // Habilitar solo si ambos campos están llenos
            ) {
                Text("Login")
            }
            // Navegar a la pantalla correspondiente si el login fue exitoso
            loginResponse?.let {
                when (userRole) {
                    "Estudiante" -> navController.navigate(AppScreen.EstudianteScreen.route)
                    "Especialista" -> navController.navigate(AppScreen.EspecialistaScreen.route)
                    else -> navController.navigate(AppScreen.HomeScreen.route)
                }
            }
            // Mostrar mensaje de error si hay alguno
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red)
                navController.navigate(AppScreen.HomeScreen.route)
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    MyApplicationTheme {
        val navController = rememberNavController()
        HomeScreen(navController = navController)
    }
}
