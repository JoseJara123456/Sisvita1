
package com.example.myapplication.data.repository

import com.example.myapplication.data.model.Login
import com.example.myapplication.network.ApiInstance
import retrofit2.Response

class LoginRepository() {

    private val service = ApiInstance.apiInstance

        suspend fun getLoginRepository() : Result<Login>{
            return try {
                val response = service.getLogin()
                println("getSolicitudesEstadoRepository"+ response)
                Result.success(response)
            }catch (e: Exception){
                Result.failure(e)
            }
        }

    }
