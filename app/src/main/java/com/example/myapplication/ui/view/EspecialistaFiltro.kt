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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.data.SelectedTestData
import com.example.myapplication.data.UserSession

@Composable
fun EspecialistaFiltro(navController: NavHostController) {
    var filterLevel by remember { mutableStateOf("") }
    var filterTreatment by remember { mutableStateOf("") }
    var fundamentacionCientifica by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }

    val nombre = UserSession.nombre ?: "User"
    val nombreUsuario = SelectedTestData.nombreUsuario ?: "Jose"
    val nombreTest = SelectedTestData.nombreTest ?: "Beck"
    val nivelAnsiedad = SelectedTestData.nivelAnsiedad ?: "leve"

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(text = "Especialista: $nombre ", fontSize = 18.sp)
            Text(text = "Estudiante: $nombreUsuario", fontSize = 18.sp)
            Text(text = "Tipo de Test: $nombreTest", fontSize = 18.sp)
            Text(text = "Nivel de Ansiedad: $nivelAnsiedad", fontSize = 18.sp)

            DropdownMenuField(
                label = "Diagnóstico de Nivel de Ansiedad",
                options = listOf("Leve", "Moderado", "Grave"),
                selectedOption = filterLevel,
                onOptionSelected = { filterLevel = it }
            )

            // Mostrar tratamientos según el tipo de test seleccionado
            val tratamientos = when (nombreTest) {
                "Test de Beck" -> listOf(
                    "Terapia Cognitivo-Conductual",
                    "Ejercicio Regular",
                    "Meditación/Relajación"
                )
                "HAM-A" -> listOf(
                    "Terapia Cognitivo-Conductual",
                    "Medicamentos Ansiolíticos",
                    "Técnicas de Respiración"
                )
                "GAD-7" -> listOf(
                    "Terapia Cognitivo-Conductual (TCC)",
                    "Meditación y atención plena",
                    "Actividad Física"
                )
                else -> emptyList()
            }

            DropdownMenuField(
                label = "Tratamiento",
                options = tratamientos,
                selectedOption = filterTreatment,
                onOptionSelected = { filterTreatment = it }
            )

            // Cuadro de texto para fundamentación científica
            TextField(
                value = fundamentacionCientifica,
                onValueChange = { fundamentacionCientifica = it },
                label = { Text("Fundamentación Científica") },
                modifier = Modifier.fillMaxWidth()
            )

            // Cuadro de texto para observaciones
            TextField(
                value = observaciones,
                onValueChange = { observaciones = it },
                label = { Text("Observaciones") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { /* TODO: Handle enviar diagnóstico */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Enviar Diagnóstico")
            }
        }
    }
}

@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(
            text = label,
            style = TextStyle(fontSize = 18.sp, color = Color(0xFF0288D1))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(8.dp)
        ) {
            Text(
                text = if (selectedOption.isEmpty()) "Seleccione una opción" else selectedOption,
                style = TextStyle(fontSize = 14.sp, color = Color.Black)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
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
