package com.innoji.todolistapp.data.api

import com.innoji.todolistapp.data.response.LoginResponse
import com.innoji.todolistapp.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("register")
    fun register(
        @Field("username") name: String,
        @Field("email") username: String,
        @Field("password") email: String,
    ): Call<RegisterResponse>

    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}