package com.example.foodorder.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodorder.data.model.SepetYemek
import com.example.foodorder.data.repository.YemekRepository
import kotlinx.coroutines.launch

class SepetViewModel : ViewModel() {

    private val repository = YemekRepository()
    val sepetYemekleri = MutableLiveData<List<SepetYemek>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    val silindi = MutableLiveData<Boolean>()
    val toplamFiyat = MutableLiveData<Int>()

    fun sepettekiYemekleriGetir() {
        viewModelScope.launch {
            loading.value = true
            try {
                val response = repository.sepettekiYemekleriGetir()
                if (response.isSuccessful) {
                    response.body()?.let { sepetResponse ->
                        if (sepetResponse.success == 1) {
                            sepetResponse.sepet_yemekler?.let { yemekler ->
                                sepetYemekleri.value = yemekler
                                toplamHesapla(yemekler)
                            } ?: run {
                                sepetYemekleri.value = emptyList()
                                toplamFiyat.value = 0
                            }
                        }
                    }
                } else {
                    sepetYemekleri.value = emptyList()
                    toplamFiyat.value = 0
                }
            } catch (e: Exception) {
                error.value = e.message
            } finally {
                loading.value = false
            }
        }
    }

    fun sepettenSil(sepetYemekId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.sepettenYemekSil(sepetYemekId)
                if (response.isSuccessful) {
                    response.body()?.let { baseResponse ->
                        if (baseResponse.success == 1) {
                            silindi.value = true
                            sepettekiYemekleriGetir() // Sepeti yenile
                        }
                    }
                }
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }

    private fun toplamHesapla(yemekler: List<SepetYemek>) {
        var toplam = 0
        yemekler.forEach { yemek ->
            toplam += yemek.yemek_fiyat.toInt() * yemek.yemek_siparis_adet.toInt()
        }
        toplamFiyat.value = toplam
    }
}
