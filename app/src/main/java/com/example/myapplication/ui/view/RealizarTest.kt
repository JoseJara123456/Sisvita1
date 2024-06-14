package com.example.myapplication.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.data.UserSession
import com.example.myapplication.navigation.AppScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.viewmodel.LoginViewModel
import com.example.myapplication.ui.viewmodel.TestViewModel

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.model.TestsYPreguntasResponse
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import com.example.myapplication.data.model.beckOptions
import com.example.myapplication.data.model.hamAOptions
import com.example.myapplication.data.model.gad7Options
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealizarTest(navController: NavController) {
    val viewModel: TestViewModel = viewModel()
    viewModel.obtenerTestsYPreguntasUsuario()
    val testsYPreguntas = viewModel.testsYPreguntas
    val errorMessage = viewModel.errorMessage

    // Estado para rastrear el test seleccionado
    var selectedTest by remember { mutableStateOf<TestsYPreguntasResponse.tipoTest?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (selectedTest != null) "Preguntas del Test" else "Realizar Test",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            if (selectedTest != null) {
                                selectedTest = null
                            } else {
                                navController.popBackStack()
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF085394))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Mostrar preguntas si hay un test seleccionado
            selectedTest?.let { test ->
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = test.nombre,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Obtener las opciones correspondientes al tipo de test
                    val opciones = when (test.tipotest_id) {
                        1 -> beckOptions
                        4 -> hamAOptions
                        5 -> gad7Options
                        else -> emptyList()
                    }

                    // Estado para rastrear las selecciones de cada pregunta
                    val seleccionadas = remember { mutableStateMapOf<Int, Int?>() }

                    LazyColumn {
                        items(test.preguntas) { pregunta ->
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = pregunta.contenido,
                                    fontSize = 18.sp,
                                    color = Color.Black,
                                    modifier = Modifier.padding(vertical = 4.dp)
                                        .fillMaxWidth(),
                                    textAlign = TextAlign.Start
                                )
                                // Mostrar las opciones como RadioButtons
                                opciones.forEach { opcion ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(vertical = 4.dp)
                                            .fillMaxWidth()
                                    ) {
                                        RadioButton(
                                            selected = seleccionadas[pregunta.preguntaid] == opcion.id,
                                            onClick = { seleccionadas[pregunta.preguntaid] = opcion.id }
                                        )
                                        Text(
                                            text = opcion.label,
                                            modifier = Modifier.padding(start = 8.dp),
                                            textAlign = TextAlign.Start
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            } ?: run {
                // Mostrar lista de tests si no hay un test seleccionado
                testsYPreguntas?.let { tests ->
                    tests.forEach { test ->
                        Button(
                            onClick = {
                                selectedTest = test
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RectangleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDFDBD7))
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = test.nombre,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Text(
                                    text = "Descripción: ${test.descripcion}",
                                    color = Color.Black,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
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
}

@Preview(showBackground = true)
@Composable
fun RealizarTestPreview() {
    // Proporciona un NavController falso y un ViewModel falso para la previsualización
    val navController = rememberNavController()
    RealizarTest(navController = navController)
}
