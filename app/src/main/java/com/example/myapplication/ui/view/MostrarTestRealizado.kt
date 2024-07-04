package com.example.myapplication.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.AppScreen
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

    // Estados para los filtros
    var filterName by remember { mutableStateOf("") }
    var filterLevel by remember { mutableStateOf("") }
    var filterType by remember { mutableStateOf("") }

    // Estado para la fila seleccionada
    var selectedRow by remember { mutableStateOf<Int?>(null) }

    // Filtrar los datos
    val filteredTests = testsYRespuestas?.filter { test ->
        (filterName.isEmpty() || test.nombre_usuario.contains(filterName, ignoreCase = true)) &&
                (filterLevel.isEmpty() || test.nivel_ansiedad.equals(filterLevel, ignoreCase = true)) &&
                (filterType.isEmpty() || test.nombre_test.equals(filterType, ignoreCase = true))
    } ?: emptyList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tests Realizados") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
            )
        },
        bottomBar = {
            if (selectedRow != null) {
                Button(
                    onClick = { /* Acción para evaluar la fila seleccionada */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Evaluar")
                }
            }
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
            // Filtros debajo uno del otro
            Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                OutlinedTextField(
                    value = filterName,
                    onValueChange = { filterName = it },
                    label = { Text("Nombre de Alumno") }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Filtro de nivel
                Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                    Text("Nivel:")
                    FilterDropdown(
                        selectedOption = filterLevel,
                        onOptionSelected = { filterLevel = it },
                        options = listOf("", "leve", "moderada", "grave")
                    )
                }

                // Filtro de tipo de test
                Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                    Text("Tipo de Test:")
                    FilterDropdown(
                        selectedOption = filterType,
                        onOptionSelected = { filterType = it },
                        options = listOf("", "Test de Beck", "Test HAM-A", "Test GAD-7")
                    )
                }
            }
            Button(
                onClick = {
                    navController.navigate(AppScreen.HeatmapScreen.route)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(Color(0xFF085394))
            ) {
                Text("Ver mapa de calor")
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp)
            ) {
                item {
                    // Encabezados de la tabla
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text("Test", fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Text("Usuario", fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Text("Fecha del Test", fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Text("Puntaje", fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Text("Nivel", fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                    }
                }
                items(filteredTests) { test ->
                    val index = filteredTests.indexOf(test)
                    val semaforoColor = when (test.nivel_ansiedad) {
                        "leve" -> Color.Green
                        "moderada" -> Color(0xFFFFA500)
                        "grave" -> Color.Red
                        else -> Color.Gray
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { selectedRow = if (selectedRow == index) null else index }
                            .background(if (index == selectedRow) Color.LightGray else Color.Transparent)
                    ) {
                        Text(test.nombre_test, fontSize = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Text(test.nombre_usuario, fontSize = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Text(test.fecha_test, fontSize = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Text(test.puntaje.toString(), fontSize = 16.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(color = semaforoColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(test.nivel_ansiedad, fontSize = 16.sp, textAlign = TextAlign.Center)
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

@Composable
fun FilterDropdown(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    options: List<String>
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { expanded = !expanded })
                .background(Color.LightGray)
                .padding(8.dp)
        ) {
            Text(
                text = if (selectedOption.isEmpty()) "Seleccionar" else selectedOption,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }

        if (expanded) {
            Column(modifier = Modifier.fillMaxWidth()) {
                options.forEach { option ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onOptionSelected(option)
                                expanded = false
                            }
                            .background(Color.White)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = option.ifEmpty { "Todos" },
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MostrarTestRealizadoPreview() {
    MostrarTestRealizado(rememberNavController())
}

