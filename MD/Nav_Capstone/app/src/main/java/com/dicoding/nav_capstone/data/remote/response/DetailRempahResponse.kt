package com.dicoding.nav_capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class DetailRempahResponse(

	@field:SerializedName("dataRempah")
	val dataRempah: DataRempah,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ResepTerkaitItem(

	@field:SerializedName("Id Resep")
	val idResep: String,

	@field:SerializedName("Related_Keyword")
	val relatedKeyword: String,

	@field:SerializedName("Nama Resep")
	val namaResep: String,

	@field:SerializedName("Image")
	val image: String
)

data class DataRempah(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("id_rempah")
	val idRempah: Int,

	@field:SerializedName("manfaat")
	val manfaat: String,

	@field:SerializedName("pengobatan_tradisional")
	val pengobatanTradisional: String,

	@field:SerializedName("resep_terkait")
	val resepTerkait: List<ResepTerkaitItem>,

	@field:SerializedName("kandungan")
	val kandungan: String,

	@field:SerializedName("minuman")
	val minuman: String,

	@field:SerializedName("daerah_penghasil")
	val daerahPenghasil: String,

	@field:SerializedName("cara_penyimpanan")
	val caraPenyimpanan: String,

	@field:SerializedName("nama_latin")
	val namaLatin: String,

	@field:SerializedName("masakan")
	val masakan: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("nama_rempah")
	val namaRempah: String
)
