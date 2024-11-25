package com.shafiq.data.model.request

import com.shafiq.domain.model.request.AddCartRequestModel
import kotlinx.serialization.Serializable

@Serializable
data class AddToCartRequest (
    val productId: Int,
    val productName: String,
    val price: Double,
    val quantity: Int,
    val userId: Int
) {
    companion object {
        fun fromCartRequestModel(addCartRequestModel: AddCartRequestModel) = AddToCartRequest(
            productId = addCartRequestModel.productId,
            productName = addCartRequestModel.productName,
            price = addCartRequestModel.price,
            quantity = addCartRequestModel.quantity,
            userId = addCartRequestModel.userId
        )
    }
}