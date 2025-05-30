package com.example.foodorder.data.model

data class YemekResponse(
    val yemekler: List<Yemek>,
    val success: Int
)