package com.dicoding.nav_capstone.ui.list

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.nav_capstone.data.remote.response.AllRempahResponse
import com.dicoding.nav_capstone.data.remote.response.ListRempahItem
import com.dicoding.nav_capstone.data.remote.retrofit.ApiConfig
import com.dicoding.nav_capstone.data.repository.RempahRepository
import com.dicoding.nav_capstone.data.repository.ResultState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel(private val rempahRepository: RempahRepository) : ViewModel() {
    private val _listRempah = MutableLiveData<List<ListRempahItem?>?>()
    val listRempah: LiveData<List<ListRempahItem?>?> = _listRempah

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getAllRempah() = rempahRepository.getAllRempah(token = String())

    fun findUser(searchResult: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRempah(searchResult)
        client.enqueue(object : Callback<AllRempahResponse> {
            override fun onResponse(
                call: Call<AllRempahResponse>,
                response: Response<AllRempahResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listRempah.value = response.body()?.listRempah

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<AllRempahResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

}