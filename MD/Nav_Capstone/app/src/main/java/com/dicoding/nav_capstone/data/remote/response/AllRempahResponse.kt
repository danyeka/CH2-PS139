package com.dicoding.nav_capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class AllRempahResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("listRempah")
	val listRempah: List<ListRempahItem>
)

data class ListRempahItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("id_rempah")
	val idRempah: Int,

	@field:SerializedName("nama_latin")
	val namaLatin: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("nama_rempah")
	val namaRempah: String
)
