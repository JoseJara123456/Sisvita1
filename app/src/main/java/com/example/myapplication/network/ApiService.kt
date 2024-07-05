package com.example.myapplication.network

import com.example.myapplication.data.model.DiagnosticoResponse
import com.example.myapplication.data.model.DiagnosticosRequest
import com.example.myapplication.data.model.EnviarRespuestasRequest
import com.example.myapplication.data.model.HeatMapResponse
import com.example.myapplication.data.model.LoginRequest
import com.example.myapplication.data.model.LoginResponse
import com.example.myapplication.data.model.RespuestaResponse
import com.example.myapplication.data.model.TestsRealizadosResponse
import com.example.myapplication.data.model.TestsYPreguntasResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("/Login2")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("/VerTest")
    suspend fun obtenerTestsYPreguntasUsuario(): TestsYPreguntasResponse

    @GET("/VerTestRealizados")
    suspend fun obtenerTestsRealizados(): TestsRealizadosResponse

    @POST("/enviarRespuestas")
    suspend fun enviarRespuestas(@Body request: EnviarRespuestasRequest): RespuestaResponse

    @GET("/heatmap")
    suspend fun getHeatMapData(): HeatMapResponse

    @POST("/Diagnosticos")
    suspend fun enviarDatosDiagnostico(@Body request: DiagnosticosRequest): DiagnosticoResponse

}
