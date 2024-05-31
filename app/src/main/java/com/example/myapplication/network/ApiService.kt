package com.example.myapplication.network

import com.example.myapplication.data.model.Login
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/Login")
    suspend fun getLogin(
        @Query("email") email: String,
        @Query("password") password: String
    ): Login
}
