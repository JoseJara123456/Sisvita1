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
import com.example.myapplication.ui.viewmodel.TestRealizadosViewModel
import androidx.compose.foundation.clickable
import androidx.compose.material3.OutlinedTextField



import android.app.DatePickerDialog

import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import java.util.*
import java.text.SimpleDateFormat
import java.util.*

import android.widget.DatePicker
import androidx.compose.material.icons.filled.Clear
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.myapplication.R
import com.example.myapplication.data.model.TestData
import com.example.myapplication.navigation.AppScreen

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
    var filterStartDate by remember { mutableStateOf("") }
    var filterEndDate by remember { mutableStateOf("") }

    // Estado para la fila seleccionada
    var selectedRow by remember { mutableStateOf<Int?>(null) }

    // Filtrar los datos
    val filteredTests = testsYRespuestas?.filter { test ->
        (filterName.isEmpty() || test.nombre_usuario.contains(filterName, ignoreCase = true)) &&
                (filterLevel.isEmpty() || test.nivel_ansiedad.equals(filterLevel, ignoreCase = true)) &&
                (filterType.isEmpty() || test.nombre_test.equals(filterType, ignoreCase = true)) &&
                (filterStartDate.isEmpty() || parseDate(test.fecha_test)?.after(parseDate(filterStartDate)) == true) &&
                (filterEndDate.isEmpty() || parseDate(test.fecha_test)?.before(parseDate(filterEndDate)) == true)
    } ?: emptyList()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.tests_realizados)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
            )
        },
        bottomBar = {
            if (selectedRow != null) {
                Button(
                    onClick = {
                        selectedRow?.let {
                            val selectedTest = filteredTests[it]
                            val testData = TestData(
                                nombreTest = selectedTest.nombre_test,
                                nombreUsuario = selectedTest.nombre_usuario,
                                nivelAnsiedad = selectedTest.nivel_ansiedad,
                                testId = selectedTest.test_id
                            )
                            viewModel.saveSelectedTestData(testData)
                            navController.navigate(AppScreen.EspecialistaFiltro.route)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(stringResource(R.string.evaluar))
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
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
                OutlinedTextField(
                    value = filterName,
                    onValueChange = { filterName = it },
                    label = { Text(stringResource(R.string.nombre_de_alumno)) }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Filtro de nivel
                Column(modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)) {
                    DropdownMenuField(
                        label = stringResource(R.string.nivel),
                        options = listOf(stringResource(R.string.leve),
                            stringResource(R.string.moderada), stringResource(R.string.grave)
                        ),
                        selectedOption = filterLevel,
                        onOptionSelected = { filterLevel = it }
                    )
                }

                // Filtro de tipo de test
                Column(modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)) {
                    DropdownMenuField(
                        label = stringResource(R.string.tipo_de_test),
                        options = listOf(stringResource(R.string.test_de_beck),
                            stringResource(R.string.test_ham_a), stringResource(R.string.test_gad_7)
                        ),
                        selectedOption = filterType,
                        onOptionSelected = { filterType = it }
                    )
                }
            }


            // Filtros de fecha en una sola fila
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DatePickerField(
                    label = stringResource(R.string.fecha_inicio),
                    selectedDate = filterStartDate,
                    onDateSelected = { filterStartDate = it },
                    onClearDate = { filterStartDate = "" },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp)
                )
                DatePickerField(
                    label = stringResource(R.string.fecha_fin),
                    selectedDate = filterEndDate,
                    onDateSelected = { filterEndDate = it },
                    onClearDate = { filterEndDate = "" },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp)
                )
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
                        Text(stringResource(R.string.test), fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Text(stringResource(R.string.usuario), fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Text(stringResource(R.string.fecha_del_test), fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Text(stringResource(R.string.puntaje), fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                        Text(stringResource(R.string.nivel), fontWeight = FontWeight.Bold, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.weight(1f))
                    }
                }
                items(filteredTests) { test ->
                    val index = filteredTests.indexOf(test)
                    val semaforoColor = when (test.nivel_ansiedad) {
                        stringResource(R.string.leve) -> Color.Green
                        stringResource(R.string.moderada) -> Color(0xFFFFA500)
                        stringResource(R.string.grave) -> Color.Red
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val displayOptions = listOf(stringResource(R.string.todos_los_datos)) + options

    Box(modifier = Modifier.fillMaxWidth()) {
        Column {
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
                    text = if (selectedOption.isEmpty()) stringResource(R.string.seleccione_una_opci_n) else selectedOption,
                    style = TextStyle(fontSize = 14.sp, color = Color.Black)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                displayOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = {
                            onOptionSelected(option.takeIf { it != "Todos los datos" } ?: "")
                            expanded = false
                        }
                    )


                }
            }
        }
    }
}

@Composable
fun DatePickerField(
    label: String,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
    onClearDate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            val date = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
            onDateSelected(date)
        },
        year, month, day
    )

    Box(modifier = modifier) {
        Column {
            Text(
                text = label,
                style = TextStyle(fontSize = 14.sp, color = Color(0xFF0288D1))
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { datePickerDialog.show() }
                ) {
                    Text(
                        text = selectedDate.ifEmpty { stringResource(R.string.seleccione_una_fecha) },
                        style = TextStyle(fontSize = 14.sp, color = Color.Black)
                    )
                }
                if (selectedDate.isNotEmpty()) {
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(onClick = onClearDate) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear Date")
                    }
                }
            }
        }
    }
}

private fun parseDate(date: String): Date? {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return try {
        format.parse(date)
    } catch (e: Exception) {
        null
    }
}

