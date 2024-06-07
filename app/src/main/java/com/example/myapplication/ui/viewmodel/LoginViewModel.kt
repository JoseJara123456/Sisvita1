package com.example.myapplication.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.LoginRequest
import com.example.myapplication.data.model.LoginResponse
import com.example.myapplication.data.repository.LoginRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var loginResponse = mutableStateOf<LoginResponse?>(null)
    var errorMessage = mutableStateOf("")

    private val loginRepository = LoginRepository()

    fun login() {
        viewModelScope.launch {
            val request = LoginRequest(email, password)
            try {
                val response = loginRepository.login(request)
                loginResponse.value = response
            } catch (e: HttpException) {
                errorMessage.value = e.message ?: "An error occurred"
            }
        }
    }
}

