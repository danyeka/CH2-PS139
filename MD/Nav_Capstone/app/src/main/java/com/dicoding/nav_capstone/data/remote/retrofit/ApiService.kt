package com.dicoding.nav_capstone.data.remote.retrofit

import com.dicoding.nav_capstone.data.remote.response.AllRempahResponse
import com.dicoding.nav_capstone.data.remote.response.ArtikelResponse
import com.dicoding.nav_capstone.data.remote.response.DetailRempahResponse
import com.dicoding.nav_capstone.data.remote.response.HomeResponse
import com.dicoding.nav_capstone.data.remote.response.LoginResponse
import com.dicoding.nav_capstone.data.remote.response.RegisterResponse
import com.dicoding.nav_capstone.data.remote.response.ResepResponse
import com.dicoding.nav_capstone.data.remote.response.ScanResponse
import com.dicoding.nav_capstone.data.repository.LoginRequest
import com.dicoding.nav_capstone.data.repository.RegisterRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse

    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("rempah")
    suspend fun getAllRempah(
        @Header("Authorization") token : String
    ): AllRempahResponse

    @GET("home")
    suspend fun getHomeData():HomeResponse

    @GET("rempah/{id}")
    suspend fun getDetailRempah(
        @Path("id") id: String
    ):DetailRempahResponse

    @GET("resep/{id}")
    suspend fun getResep(
        @Path("id") id: String
    ):ResepResponse

    @GET("artikel/{id}")
    suspend fun getArtikel(
        @Path("id") id: String
    ): ArtikelResponse

    @GET("search/rempah") // Specify the base URL for the endpoint
    fun getRempah(
        @Query("q") query: String
    ) : Call<AllRempahResponse>

    @Multipart
    @POST("upload")
    suspend fun scanImage(
        @Part file: MultipartBody.Part
    ): ScanResponse


}