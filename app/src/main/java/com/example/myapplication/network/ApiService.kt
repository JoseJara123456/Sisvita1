package com.example.myapplication.network

import com.example.myapplication.data.model.LoginRequest
import com.example.myapplication.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/Login2")
    suspend fun login(@Body request: LoginRequest): LoginResponse

}

