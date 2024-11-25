package com.shafiq.domain.repository

import com.shafiq.domain.model.CartModel
import com.shafiq.domain.model.request.AddCartRequestModel
import com.shafiq.domain.network.ResultWrapper

interface CartRepository {
    suspend fun addProductToCart(request: AddCartRequestModel): ResultWrapper<CartModel>
}