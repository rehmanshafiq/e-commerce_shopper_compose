package com.shafiq.domain.usecase

import com.shafiq.domain.repository.CartRepository

class CartSummaryUseCase(private val repository: CartRepository) {
    suspend fun execute(userId: Int) = repository.getCartSummary(userId)
}