@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.myapplication.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.example.myapplication.R
import com.example.myapplication.navigation.AppScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: LoginViewModel = viewModel()
    // Observar el estado de loginResponse y errorMessage desde el ViewModel
    val loginResponse by viewModel.loginResponse
    val errorMessage by viewModel.errorMessage
    val userRole by viewModel.userRole
    val idUsuario by viewModel.idUsuario


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
                //.padding(16.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF0D47A1), Color(0xFF512DA8))
                    )
                    ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.stack),
                contentDescription = "sisvita-transformed",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = "Bienvenido a Sisvita",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            // Campo de texto para el correo
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                label = { Text("Correo",color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White
            )
            )
            // Campo de texto para la contraseña
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                label = { Text("Contraseña",color = Color.White) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    cursorColor = Color.White
            )
            )
            // Botón de login
            Button(
                onClick = {
                    if (viewModel.email.isNotEmpty() && viewModel.password.isNotEmpty()) {
                        viewModel.login() // Llamar al método de login en el ViewModel
                    }
                },
                modifier = Modifier.fillMaxWidth()
                .border(1.dp,  Color(0xFF512DA8)),
            //colors = ButtonDefaults.buttonColors(Color(0xFF085394)),
                enabled = viewModel.email.isNotEmpty() && viewModel.password.isNotEmpty() // Habilitar solo si ambos campos están llenos
            ) {
                Text("Login", color = Color.White)
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
