package com.innoji.todolistapp.data.network

import com.innoji.todolistapp.data.request.LoginRequest
import com.innoji.todolistapp.data.request.RegisterRequest
import com.innoji.todolistapp.data.response.LoginResponse
import com.innoji.todolistapp.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("register")
    fun register(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>
}