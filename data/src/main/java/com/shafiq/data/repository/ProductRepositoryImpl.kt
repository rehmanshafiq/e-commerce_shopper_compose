package com.shafiq.data.repository

import com.shafiq.domain.model.Product
import com.shafiq.domain.network.NetworkService
import com.shafiq.domain.network.ResultWrapper
import com.shafiq.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val networkService: NetworkService
) : ProductRepository {

    override suspend fun getProducts(category: String?): ResultWrapper<List<Product>> {
        return networkService.getProducts(category)
    }
}