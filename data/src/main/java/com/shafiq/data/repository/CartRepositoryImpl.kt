package com.shafiq.data.repository

import com.shafiq.domain.model.CartModel
import com.shafiq.domain.model.request.AddCartRequestModel
import com.shafiq.domain.network.NetworkService
import com.shafiq.domain.network.ResultWrapper
import com.shafiq.domain.repository.CartRepository

class CartRepositoryImpl(
    private val networkService: NetworkService
): CartRepository {

    override suspend fun addProductToCart(request: AddCartRequestModel): ResultWrapper<CartModel> {
        return networkService.addProductToCart(request)
    }
}