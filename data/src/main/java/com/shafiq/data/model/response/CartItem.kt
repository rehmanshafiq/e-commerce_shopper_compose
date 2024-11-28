package com.shafiq.data.model.response

import com.shafiq.domain.model.CartItemModel
import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    val id: Int,
    val productId: Int,
    val price: Double,
    val imageUrl: String,
    val quantity: Int,
    val productName: String
) {
    fun toCartItemModel(): CartItemModel {
        return CartItemModel(
            id = id,
            productId = productId,
            price = price,
            imageUrl = imageUrl,
            quantity = quantity,
            productName = productName
        )
    }
}
