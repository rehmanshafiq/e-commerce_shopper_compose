package com.shafiq.data.model.response

import com.shafiq.domain.model.CartSummary
import kotlinx.serialization.Serializable

@Serializable
data class CartSummaryResponse(
    val `data`: Summary,
    val msg: String
) {
    fun toCartSummary() = CartSummary(
        data = `data`.toSummaryData(),
        msg = msg
    )
}
