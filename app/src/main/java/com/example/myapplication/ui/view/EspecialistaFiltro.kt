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
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.material3.TextField
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.model.RespuestaRequest
import com.example.myapplication.data.model.TestsYPreguntasResponse
import com.example.myapplication.navigation.AppScreen
import com.example.myapplication.ui.viewmodel.TestRealizadosViewModel
import com.example.myapplication.ui.viewmodel.TestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  EspecialistaFiltro(navController: NavHostController) {
    val viewModel: TestRealizadosViewModel = viewModel()

    // Estado para los filtros
    var fechaTest by remember { mutableStateOf("") }
    var tipoTest by remember { mutableStateOf("") }
    var puntaje by remember { mutableStateOf("") }

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
            // Campos de entrada para los filtros
            TextField(
                value = fechaTest,
                onValueChange = { fechaTest = it },
                label = { Text("Fecha del Test (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
            TextField(
                value = tipoTest,
                onValueChange = { tipoTest = it },
                label = { Text("Tipo de Test") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
            TextField(
                value = puntaje,
                onValueChange = { puntaje = it },
                label = { Text("Puntaje Mínimo") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
            Button(
                onClick = {
                    // Llama a una función en el ViewModel para filtrar los tests
                    viewModel.filtrarTests(fechaTest, tipoTest, puntaje)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Filtrar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (testsYRespuestas != null && testsYRespuestas.isNotEmpty()) {
                // Encabezados de la tabla
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Test",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(2f)
                    )
                    Text(
                        text = "Usuario",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Fecha del Test",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Puntaje",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
                // Filas de la tabla
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
fun EspecialistaFiltro() {
    val navController = rememberNavController()
    EspecialistaFiltro(navController = navController)
    //EspecialistaFiltro(rememberNavController())
}