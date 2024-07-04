
package com.example.myapplication.data.repository

import com.example.myapplication.data.model.LoginRequest
import com.example.myapplication.data.model.LoginResponse
import com.example.myapplication.network.ApiInstance

class LoginRepository {

    private val apiService = ApiInstance.apiInstance

    suspend fun login(request: LoginRequest): LoginResponse {
        return apiService.login(request)
    }
}

