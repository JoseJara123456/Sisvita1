package com.example.myapplication.ui.view


import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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

// Anotación para indicar que se utiliza una API experimental de Material3
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MostrarTestRealizado(navController: NavController) {
    // Inicializar el ViewModel para acceder y manipular los datos de los tests realizados
    val viewModel: TestRealizadosViewModel = viewModel()

    // Llamar a la función para obtener los tests realizados cuando se inicia la composición
    viewModel.obtenerTestsRealizados()

    // Observar los datos del ViewModel para reaccionar a cambios
    val testsYRespuestas = viewModel.testsYRespuestas
    val errorMessage = viewModel.errorMessage

    // Estructura básica de la pantalla con una barra superior
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tests Realizados") },  // Título de la barra superior
                navigationIcon = {
                    // Botón para regresar a la pantalla anterior
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    )
    { paddingValues ->
        // Columna para organizar verticalmente los elementos dentro de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Condición para mostrar la lista de tests si existe algún elemento
            if (testsYRespuestas != null && testsYRespuestas.isNotEmpty()) {
                // Fila para encabezados de la tabla de resultados
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    // Encabezados para las columnas de la tabla
                    listOf("Test", "Usuario", "Fecha del Test", "Puntaje").forEach { header ->
                        Text(
                            text = header,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                // Filas para mostrar cada test realizado
                testsYRespuestas.forEach { test ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = test.nombre_test,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(2f)
                        )
                        Text(
                            text = test.nombre_usuario,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = test.fecha_test,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = test.puntaje.toString(),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            } else {
                // Mensaje cuando no hay tests disponibles
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

            // Mostrar mensaje de error si existe alguno
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

// Previsualización de la Composable para verificar su apariencia en tiempo de diseño
@Preview
@Composable
fun MostrarTestRealizadoPreview() {
    MostrarTestRealizado(rememberNavController())
}

