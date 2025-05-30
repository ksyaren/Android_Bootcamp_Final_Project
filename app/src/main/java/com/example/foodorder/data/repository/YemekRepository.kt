package com.example.foodorder.data.repository

import com.example.foodorder.data.api.RetrofitClient
import com.example.foodorder.data.model.*
import com.example.foodorder.utils.Constants

class YemekRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun tumYemekleriGetir() = apiService.tumYemekleriGetir()

    suspend fun sepeteYemekEkle(
        yemekAdi: String,
        yemekResimAdi: String,
        yemekFiyat: Int,
        yemekSiparisAdet: Int
    ) = apiService.sepeteYemekEkle(
        yemekAdi, yemekResimAdi, yemekFiyat, yemekSiparisAdet, Constants.KULLANICI_ADI
    )

    suspend fun sepettekiYemekleriGetir() =
        apiService.sepettekiYemekleriGetir(Constants.KULLANICI_ADI)

    suspend fun sepettenYemekSil(sepetYemekId: Int) =
        apiService.sepettenYemekSil(sepetYemekId, Constants.KULLANICI_ADI)
}