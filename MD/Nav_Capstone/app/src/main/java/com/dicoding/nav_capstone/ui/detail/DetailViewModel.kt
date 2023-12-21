package com.dicoding.nav_capstone.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.nav_capstone.data.remote.response.DetailRempahResponse
import com.dicoding.nav_capstone.data.repository.RempahRepository

class DetailViewModel(private val rempahRepository: RempahRepository): ViewModel() {

    private val _userName = MutableLiveData<DetailRempahResponse>()
    val userName: LiveData<DetailRempahResponse> = _userName

    fun getDetailRempah (id:String) = rempahRepository.getDetailRempah(id)
}