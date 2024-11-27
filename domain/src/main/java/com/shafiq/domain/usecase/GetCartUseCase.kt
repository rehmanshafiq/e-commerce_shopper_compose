package com.shafiq.domain.usecase

import com.shafiq.domain.repository.CartRepository

class GetCartUseCase(private val cartRepository: CartRepository) {
    suspend fun execute() = cartRepository.getCart()
}