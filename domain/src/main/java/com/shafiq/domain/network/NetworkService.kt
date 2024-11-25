package com.shafiq.domain.network

import com.shafiq.domain.model.CartModel
import com.shafiq.domain.model.CategoriesListModel
import com.shafiq.domain.model.ProductListModel
import com.shafiq.domain.model.request.AddCartRequestModel

interface NetworkService {
    suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel>
    suspend fun getCategories(): ResultWrapper<CategoriesListModel>
    suspend fun addProductToCart(request: AddCartRequestModel): ResultWrapper<CartModel>
}

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Failure(val exception: Exception) : ResultWrapper<Nothing>()
}