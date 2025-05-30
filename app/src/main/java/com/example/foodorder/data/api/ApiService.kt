package com.example.foodorder.data.api

import com.example.foodorder.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("tumYemekleriGetir.php")
    suspend fun tumYemekleriGetir(): Response<YemekResponse>

    @FormUrlEncoded
    @POST("sepeteYemekEkle.php")
    suspend fun sepeteYemekEkle(
        @Field("yemek_adi") yemekAdi: String,
        @Field("yemek_resim_adi") yemekResimAdi: String,
        @Field("yemek_fiyat") yemekFiyat: Int,
        @Field("yemek_siparis_adet") yemekSiparisAdet: Int,
        @Field("kullanici_adi") kullaniciAdi: String
    ): Response<BaseResponse>

    @FormUrlEncoded
    @POST("sepettekiYemekleriGetir.php")
    suspend fun sepettekiYemekleriGetir(
        @Field("kullanici_adi") kullaniciAdi: String
    ): Response<SepetResponse>

    @FormUrlEncoded
    @POST("sepettenYemekSil.php")
    suspend fun sepettenYemekSil(
        @Field("sepet_yemek_id") sepetYemekId: Int,
        @Field("kullanici_adi") kullaniciAdi: String
    ): Response<BaseResponse>
}