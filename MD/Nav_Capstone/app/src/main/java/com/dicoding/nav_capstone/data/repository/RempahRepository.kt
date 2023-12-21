package com.dicoding.nav_capstone.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dicoding.nav_capstone.data.local.model.SessionModel
import com.dicoding.nav_capstone.data.local.preferences.SessionPreferences
import com.dicoding.nav_capstone.data.remote.response.ErrorResponse
import com.dicoding.nav_capstone.data.remote.response.LoginResponse
import com.dicoding.nav_capstone.data.remote.response.ScanResponse
import com.dicoding.nav_capstone.data.remote.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class RempahRepository constructor(
    private val apiService: ApiService,
    private val sessionPreferences: SessionPreferences

) {
    companion object {
        @Volatile
        private var instance: RempahRepository? = null

        fun getInstance(
            apiService: ApiService,
            sessionPreferences: SessionPreferences
        ): RempahRepository =
            instance ?: synchronized(this) {
                instance ?: RempahRepository(apiService, sessionPreferences)
            }.also { instance = it }
    }

    fun register(name: String, email: String, password: String) = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.register(RegisterRequest(name, email, password))
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultState.Error(errorMessage))
        }
    }

    fun login(email: String, password: String): LiveData<ResultState<LoginResponse>> = liveData {
        emit(ResultState.Loading)
        try {
            val response = apiService.login(LoginRequest(email, password))
            emit(ResultState.Success(response))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultState.Error(errorMessage))
        }
    }


    fun getSession(): Flow<SessionModel> {
        return sessionPreferences.getSession()
    }

    suspend fun saveSession(sessionModel: SessionModel) {
        sessionPreferences.saveSession(sessionModel)
    }


    suspend fun logOut() {
        try {
            sessionPreferences.logOut()
        } catch (e: Exception) {
            Log.e("RempahRepository", "Error during logout", e)
        }
    }

    fun getAllRempah(token: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getAllRempah("Bearer $token")
            if (!successResponse.error) {
                emit(ResultState.Success(successResponse))
            } else {
                emit(ResultState.Error(successResponse.message))
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Gagal mengambil data rempah"))
        }
    }

    fun getHomeData() = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getHomeData()
            if (!successResponse.error) {
                emit(ResultState.Success(successResponse))
            } else {
                emit(ResultState.Error(successResponse.message))
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Gagal mengambil data beranda"))
        }
    }

    fun getDetailRempah(id: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getDetailRempah(id)
            if (!successResponse.error) {
                emit(ResultState.Success(successResponse))
            } else {
                emit(ResultState.Error(successResponse.message))
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Gagal mengambil data rempah"))
        }
    }

    fun getResep(id: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getResep(id)
            if (!successResponse.error) {
                emit(ResultState.Success(successResponse))
            } else {
                emit(ResultState.Error(successResponse.message))
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Gagal mengambil data rempah"))
        }
    }

    fun getArtikel(id: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getArtikel(id)
            if (!successResponse.error) {
                emit(ResultState.Success(successResponse))
            } else {
                emit(ResultState.Error(successResponse.message))
            }
        } catch (e: Exception) {
            emit(ResultState.Error("Gagal mengambil data artikel"))
        }
    }

    fun scanImage(imageFile: File) = liveData {
        emit(ResultState.Loading)
        val requestImageFile = imageFile.asRequestBody("file/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            imageFile.name,
            requestImageFile
        )
        try {
            val successResponse = apiService.scanImage(multipartBody)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ScanResponse::class.java)
            emit(errorResponse.message.let { ResultState.Error(it) })
        }

    }

}