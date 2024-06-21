package com.example.myapplication.network

import com.example.myapplication.data.model.LoginRequest
import com.example.myapplication.data.model.LoginResponse
import com.example.myapplication.data.model.TestsYPreguntasResponse
import com.example.myapplication.data.model.TestsRealizadosResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.myapplication.data.UserSession
interface ApiService {

    @POST("/Login2")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("/VerTest")
    suspend fun obtenerTestsYPreguntasUsuario(): TestsYPreguntasResponse

    @GET("/VerTestRealizados")
    suspend fun obtenerTestsRealizados(): TestsRealizadosResponse

}

