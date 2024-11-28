package com.shafiq.domain.model

data class CartItemModel(
    val id: Int,
    val productId: Int,
    val price: Double,
    val imageUrl: String,
    val quantity: Int,
    val productName: String
)
