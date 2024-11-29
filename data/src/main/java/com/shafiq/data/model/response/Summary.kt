package com.shafiq.data.model.response

import com.shafiq.domain.model.SummaryData
import kotlinx.serialization.Serializable

@Serializable
data class Summary(
    val discount: Double,
    val items: List<CartItem>,
    val shipping: Double,
    val subTotal: Double,
    val tax: Double,
    val total: Double
) {
    fun toSummaryData()= SummaryData(
        discount = discount,
        items = items.map { it.toCartItemModel() },
        shipping = shipping,
        subTotal = subTotal,
        tax = tax,
        total = total
    )
}
