package com.shafiq.domain.model

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val category: String,
    val description: String,
    val image: String
) {
    val priceString: String
        get() = "$price"
}
