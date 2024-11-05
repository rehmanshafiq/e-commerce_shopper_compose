package com.shafiq.domain.repository

import com.shafiq.domain.model.Product
import com.shafiq.domain.network.ResultWrapper

interface ProductRepository {

    suspend fun getProducts(): ResultWrapper<List<Product>>
}