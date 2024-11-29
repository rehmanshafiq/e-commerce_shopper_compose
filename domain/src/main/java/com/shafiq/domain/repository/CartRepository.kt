package com.shafiq.domain.repository

import com.shafiq.domain.model.CartItemModel
import com.shafiq.domain.model.CartModel
import com.shafiq.domain.model.request.AddCartRequestModel
import com.shafiq.domain.network.ResultWrapper

interface CartRepository {

    suspend fun addProductToCart(
        request: AddCartRequestModel
    ): ResultWrapper<CartModel>

    suspend fun getCart(): ResultWrapper<CartModel>
    suspend fun updateQuantity(cartItemModel: CartItemModel): ResultWrapper<CartModel>
    suspend fun deleteItem(cartItemId: Int, userId: Int): ResultWrapper<CartModel>
}