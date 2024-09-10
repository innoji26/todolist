package com.innoji.todolistapp.data.response

import com.google.gson.annotations.SerializedName

data class AddChecklistResponse(

	@field:SerializedName("data")
	val data: DataChecklist,

	@field:SerializedName("errorMessage")
	val errorMessage: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("statusCode")
	val statusCode: Int
)

data class DataChecklist(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("items")
	val items: String,

	@field:SerializedName("checklistCompletionStatus")
	val checklistCompletionStatus: Boolean
)
