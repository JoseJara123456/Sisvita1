package com.example.myapplication.network

import com.example.myapplication.data.model.LoginRequest
import com.example.myapplication.data.model.LoginResponse
import com.example.myapplication.data.model.TestsYPreguntasResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Path
interface ApiService {
    @POST("/Login2")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("/RealizarTest/{idUsuario}")
    suspend fun obtenerTestsYPreguntasUsuario(@Path("idUsuario") idUsuario: Int): TestsYPreguntasResponse

}

