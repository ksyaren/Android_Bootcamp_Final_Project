package com.example.foodorder.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorder.data.model.Yemek
import com.example.foodorder.data.repository.YemekRepository
import kotlinx.coroutines.launch

class AnasayfaViewModel : ViewModel() {

    private val repository = YemekRepository()
    val yemekListesi = MutableLiveData<List<Yemek>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun yemekleriGetir() {
        viewModelScope.launch {
            loading.value = true
            try {
                val response = repository.tumYemekleriGetir()
                if (response.isSuccessful) {
                    response.body()?.let { yemekResponse ->
                        if (yemekResponse.success == 1) {
                            yemekListesi.value = yemekResponse.yemekler
                        }
                    }
                } else {
                    error.value = "Yemekler yüklenirken hata oluştu"
                }
            } catch (e: Exception) {
                error.value = e.message
            } finally {
                loading.value = false
            }
        }
    }
}