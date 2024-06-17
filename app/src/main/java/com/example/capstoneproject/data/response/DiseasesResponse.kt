package com.example.capstoneproject.data.response

data class DiseasesResponse(
	val data: ArrayList<DataItem>,
	val error: Boolean? = null,
	val status: String? = null
)

data class DataItem(
	val treatment: String? = null,
	val imageURL: String? = null,
	val name: String? = null,
	val description: String? = null,
	val prevention: String? = null
)

