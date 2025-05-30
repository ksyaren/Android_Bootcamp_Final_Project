package com.example.foodorder.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorder.data.repository.YemekRepository
import kotlinx.coroutines.launch

class DetayViewModel : ViewModel() {

    private val repository = YemekRepository()
    val sepeteEklendi = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun sepeteEkle(
        yemekAdi: String,
        yemekResimAdi: String,
        yemekFiyat: Int,
        adet: Int
    ) {
        viewModelScope.launch {
            loading.value = true
            try {
                val response = repository.sepeteYemekEkle(
                    yemekAdi, yemekResimAdi, yemekFiyat, adet
                )
                if (response.isSuccessful) {
                    response.body()?.let { baseResponse ->
                        if (baseResponse.success == 1) {
                            sepeteEklendi.value = true
                        } else {
                            error.value = "Sepete eklenirken hata oluştu"
                        }
                    }
                } else {
                    error.value = "Sepete eklenirken hata oluştu"
                }
            } catch (e: Exception) {
                error.value = e.message
            } finally {
                loading.value = false
            }
        }
    }
}