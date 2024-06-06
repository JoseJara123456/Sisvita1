package com.example.myapplication.network

import com.example.myapplication.data.model.Login
import retrofit2.http.GET

interface ApiService {
    @GET("/Login")
    suspend fun getLogin( ) : Login

}

