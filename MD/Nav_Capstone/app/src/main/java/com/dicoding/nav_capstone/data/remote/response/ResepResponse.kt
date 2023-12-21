package com.dicoding.nav_capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResepResponse(

	@field:SerializedName("dataResep")
	val dataResep: DataResep,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class DataResep(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("related_keyword")
	val relatedKeyword: String,

	@field:SerializedName("nama_resep")
	val namaResep: String,

	@field:SerializedName("ingredients")
	val ingredients: List<String>,

	@field:SerializedName("id_resep")
	val idResep: Int,

	@field:SerializedName("nama_rempah")
	val namaRempah: String,

	@field:SerializedName("steps")
	val steps: List<String>
)
