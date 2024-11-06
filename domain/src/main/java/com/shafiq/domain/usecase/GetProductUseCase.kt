package com.shafiq.domain.usecase

import com.shafiq.domain.repository.ProductRepository

class GetProductUseCase(
    private val repository: ProductRepository
) {
    suspend fun execute() = repository.getProducts()
}