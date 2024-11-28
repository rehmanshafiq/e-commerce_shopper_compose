package com.shafiq.domain.usecase

import com.shafiq.domain.model.CartItemModel
import com.shafiq.domain.model.CartModel
import com.shafiq.domain.network.ResultWrapper
import com.shafiq.domain.repository.CartRepository

class UpdateQuantityUseCase(private val cartRepository: CartRepository) {

    suspend fun execute(cartItemModel: CartItemModel) = cartRepository.updateQuantity(cartItemModel)
}