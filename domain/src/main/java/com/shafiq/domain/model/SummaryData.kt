package com.shafiq.domain.model

data class SummaryData(
    val discount: Double,
    val items: List<CartItemModel>,
    val shipping: Double,
    val subTotal: Double,
    val tax: Double,
    val total: Double
)
