package com.example.myapplication.ui.view

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.ui.viewmodel.HeatMapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeatmapScreen(navController: NavHostController? = null) {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    val viewModel: HeatMapViewModel = viewModel()

    viewModel.getDataHeatMap()
    val heatMapData = viewModel.heatMapData

    var filterType by remember { mutableStateOf("") }

    // Obtener los nombres de los tipos de tests para el filtro
    val testNames = heatMapData?.map { it.test_nombre }?.distinct() ?: listOf()

    // Aplicar el filtro para tipo de test
    val filteredHeatMap = heatMapData?.filter { heatmap ->
        filterType.isEmpty() || heatmap.test_nombre == filterType
    } ?: emptyList()

    // Calcular el promedio de los niveles de ansiedad para cada ubicación
    val averagedHeatMapData = filteredHeatMap.groupBy { LatLng(it.latitud, it.longitud) }
        .mapValues { entry ->
            entry.value.map { it.nivel_ansiedad }.average()
        }
        .map { (latLng, avgNivel) ->
            WeightedLatLng(latLng, avgNivel)
        }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mapa de calor") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (navController != null) {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                    DropdownMenuField(
                        label = "Tipo de Test",
                        options = testNames,
                        selectedOption = filterType,
                        onOptionSelected = { filterType = it }
                    )
                }
            }

            AndroidView(
                factory = { mapView },
                modifier = Modifier.fillMaxSize(),
                update = { mapView ->
                    mapView.getMapAsync(OnMapReadyCallback { googleMap ->
                        googleMap.uiSettings.isZoomControlsEnabled = true
                        googleMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    -12.046374,
                                    -77.042793
                                ), 10f
                            )
                        )

                        googleMap.clear()

                        if (averagedHeatMapData.isNotEmpty()) {
                            val provider = HeatmapTileProvider.Builder()
                                .weightedData(averagedHeatMapData)
                                .radius(50)
                                .opacity(1.0)
                                .build()

                            googleMap.addTileOverlay(TileOverlayOptions().tileProvider(provider))
                        } else {
                            Log.d("HeatmapScreen", "No hay datos para heatmap")
                        }
                    })
                }
            )
        }
    }
}