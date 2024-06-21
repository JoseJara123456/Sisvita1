package com.example.myapplication.ui.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.model.TestsRealizadosResponse


import androidx.compose.material3.*


import com.example.myapplication.ui.viewmodel.TestRealizadosViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MostrarTestRealizado(navController: NavController) {
    val viewModel: TestRealizadosViewModel = viewModel()

    // Obtenemos los tests realizados al iniciar la composición
    viewModel.obtenerTestsRealizados()

    // Observamos los datos del ViewModel
    val testsYRespuestas = viewModel.testsYRespuestas
    val errorMessage = viewModel.errorMessage

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tests Realizados") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (testsYRespuestas != null && testsYRespuestas.isNotEmpty()) {
                // Encabezados de la tabla
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text("Test", fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(2f))
                    Text("Usuario", fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                    Text("Fecha del Test", fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                    Text("Puntaje", fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                }
                // Filas de la tabla
                testsYRespuestas.forEach { test ->
                    val semaforoColor = when (test.nombre_test) {
                        "Test de Beck" -> when {
                            test.puntaje <= 21 -> Color.Green // Ansiedad muy baja
                            test.puntaje in 22..35 -> Color(0xFFFFA500) // Ansiedad moderada (ámbar)
                            else -> Color.Red // Ansiedad severa
                        }
                        "Test HAM-A" -> when {
                            test.puntaje <= 23 -> Color.Green // Ansiedad muy baja
                            test.puntaje in 23..47 -> Color(0xFFFFA500) // Ansiedad moderada (ámbar)
                            else -> Color.Red // Ansiedad severa
                        }
                        "Test GAD-7" -> when {
                            test.puntaje <= 9 -> Color.Green // Estrés muy bajo
                            test.puntaje in 10..19 -> Color(0xFFFFA500) // Estrés moderado (ámbar)
                            else -> Color.Red // Estrés severo
                        }
                        else -> when {
                            test.puntaje >= 80 -> Color.Green
                            test.puntaje >= 50 -> Color.Yellow
                            else -> Color.Red
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(test.nombre_test, fontSize = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(2f))
                        Text(test.nombre_usuario, fontSize = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Text(test.fecha_test, fontSize = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Box(modifier = Modifier.weight(1f).background(color = semaforoColor), contentAlignment = Alignment.Center) {
                            Text(test.puntaje.toString(), fontSize = 16.sp, textAlign = TextAlign.Center)
                        }
                    }
                }
            } else {
                Text(
                    text = "No hay tests realizados.",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                )
            }

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun MostrarTestRealizadoPreview() {
    MostrarTestRealizado(rememberNavController())
}
