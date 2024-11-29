package com.shafiq.domain.usecase

import com.shafiq.domain.repository.CartRepository

class DeleteProductUseCase(private val cartRepository: CartRepository) {

    suspend fun execute(cartItemId: Int, userId: Int) = cartRepository.deleteItem(cartItemId, userId)
}