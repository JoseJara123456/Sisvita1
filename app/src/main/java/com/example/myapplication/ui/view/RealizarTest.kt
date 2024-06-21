package com.example.myapplication.ui.view

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.data.model.TestsYPreguntasResponse
import com.example.myapplication.ui.viewmodel.TestViewModel
import com.example.myapplication.data.model.RespuestaRequest
import androidx.compose.runtime.*

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RealizarTest(navController: NavController, usuarioId: Int) {
    val viewModel: TestViewModel = viewModel()
    viewModel.obtenerTestsYPreguntasUsuario()
    val testsYPreguntas = viewModel.testsYPreguntas
    val errorMessage = viewModel.errorMessage

    var selectedTest by remember { mutableStateOf<TestsYPreguntasResponse.tipoTest?>(null) }
    val selectedOptions = remember { mutableStateMapOf<Int, Int>() }
    var showError by remember { mutableStateOf(false) }

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
                    if (selectedTest != null) {
                        IconButton(onClick = { selectedTest = null }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }
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
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            selectedTest?.let { test ->
                LazyColumn {
                    item {
                        Text(
                            text = test.nombre,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    itemsIndexed(test.preguntas ?: emptyList()) { index, pregunta ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = "${index + 1}. ${pregunta.contenido}",
                                fontSize = 18.sp,
                                color = Color.Black,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                            test.opciones.filter { it.tipotest_id == pregunta.tipotest_id }.forEach { opcion ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 16.dp, top = 4.dp, bottom = 4.dp)
                                ) {
                                    RadioButton(
                                        selected = selectedOptions[pregunta.preguntaid] == opcion.opcionid,
                                        onClick = {
                                            selectedOptions[pregunta.preguntaid] = opcion.opcionid
                                        }
                                    )
                                    Text(
                                        text = opcion.contenido,
                                        fontSize = 16.sp,
                                        color = Color.Black,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = {
                                if (selectedOptions.size < (test.preguntas?.size ?: 0)) {
                                    showError = true
                                } else {
                                    showError = false
                                    val respuestas = selectedOptions.map { (preguntaId, opcionId) ->
                                        RespuestaRequest(preguntaId, opcionId)
                                    }
                                    Log.d("f:","$usuarioId, $respuestas")
                                    viewModel.enviarRespuestas(usuarioId, respuestas)
                                    selectedTest = null
                                    selectedOptions.clear()
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E8FE), contentColor = Color.Black)
                        ) {
                            Text(
                                text = "Enviar Respuestas",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        }
                        if (showError) {
                            Text(
                                text = "Por favor, responde todas las preguntas antes de enviar.",
                                color = Color.Red,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }
                }
            } ?: run {
                testsYPreguntas?.let { tests ->
                    tests.forEach { test ->
                        Button(
                            onClick = {
                                selectedTest = test
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .shadow(4.dp, shape = RoundedCornerShape(12.dp))
                                .border(width = 3.dp, color = Color(0xFF8BA4E7), shape = RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE0E8FE), contentColor = Color.Black)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                Text(
                                    text = test.nombre,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.Black,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Text(
                                    text = test.descripcion,
                                    fontSize = 18.sp,
                                    color = Color(0xFF1E1F22),
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