package com.innoji.todolistapp.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: DataLogin,

	@field:SerializedName("errorMessage")
	val errorMessage: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

data class DataLogin(

	@field:SerializedName("token")
	val token: String
)
