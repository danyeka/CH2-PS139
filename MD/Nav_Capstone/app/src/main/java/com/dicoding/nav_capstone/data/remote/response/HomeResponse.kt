package com.dicoding.nav_capstone.data.remote.response

import com.google.gson.annotations.SerializedName

data class HomeResponse(

	@field:SerializedName("recommendedRempah")
	val recommendedRempah: List<RecommendedRempahItem>,

	@field:SerializedName("articleList")
	val articleList: List<ArticleListItem>,

	@field:SerializedName("dictionaryRempah")
	val dictionaryRempah: List<DictionaryRempahItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("randomInfo")
	val randomInfo: RandomInfo
)

data class RecommendedRempahItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("id_rempah")
	val idRempah: Int,

	@field:SerializedName("nama_rempah")
	val namaRempah: String
)

data class DictionaryRempahItem(

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

data class RandomInfo(

	@field:SerializedName("id_info")
	val idInfo: Int,

	@field:SerializedName("isi_info")
	val isiInfo: String
)

data class ArticleListItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("judul_artikel")
	val judulArtikel: String,

	@field:SerializedName("id_artikel")
	val idArtikel: Int
)
