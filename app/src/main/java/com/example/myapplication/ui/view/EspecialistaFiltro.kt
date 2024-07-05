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
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.example.yourapp.ui.theme.YourAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  EspecialistaFiltro(navController: NavHostController) {
    var anxietyLevel by remember { mutableStateOf(TextFieldValue("")) }
    var date by remember { mutableStateOf(TextFieldValue("")) }
    var observations by remember { mutableStateOf(TextFieldValue("")) }

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
            //horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Especialista:", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(18.dp))
            Text(text = "Estudiante:", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(18.dp))
            Text(text = "Fecha", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(18.dp))
            Text(text = "Tipo de Test", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = anxietyLevel,
                onValueChange = { anxietyLevel = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Nivel de Ansiedad", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(30.dp))
            TextField(
                value = date,
                onValueChange = { date = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Observaciones:", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                value = observations,
                onValueChange = { observations = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(70.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = { /* TODO: Handle Notificar Solicitar Cita */ }) {
                    Text(text = "Notificar ")
                }
                Button(onClick = { /* TODO: Handle Observado */ }) {
                    Text(text = "Listo")
                }
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