package com.example.myapplication.data.model
data class HeatMapResponse(
    val message: String,
    val status: Int,
    val data: List<HeatMapData>
){
    data class HeatMapData(
        val test_id: Int,
        val usuarioid: Int,
        val nivel_ansiedad: Int,
        val ubigeoid: Int,
        val latitud: Double,
        val longitud: Double
    )

}

