package com.shafiq.domain.usecase

import com.shafiq.domain.model.request.AddCartRequestModel
import com.shafiq.domain.repository.CartRepository

class AddToCartUseCase(private val cartRepository: CartRepository) {

    suspend fun execute(request: AddCartRequestModel) = cartRepository.addProductToCart(request)
}