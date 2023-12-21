package com.dicoding.nav_capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class ArtikelResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("dataArtikel")
	val dataArtikel: DataArtikel
)

data class DataArtikel(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("judul_artikel")
	val judulArtikel: String,

	@field:SerializedName("isi_artikel")
	val isiArtikel: String,

	@field:SerializedName("id_artikel")
	val idArtikel: Int,

	@field:SerializedName("rempah_terkait")
	val rempahTerkait: String,

	@field:SerializedName("sumber_artikel")
	val sumberArtikel: String
)
