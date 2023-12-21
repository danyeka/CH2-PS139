package com.dicoding.nav_capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("error")
	val error: String,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("id_rempah")
	val idRempah: Int,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("nama_rempah")
	val namaRempah: String
)
