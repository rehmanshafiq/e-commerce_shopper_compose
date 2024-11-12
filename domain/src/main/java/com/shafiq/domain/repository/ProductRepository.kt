package com.shafiq.domain.repository

import com.shafiq.domain.model.ProductListModel
import com.shafiq.domain.network.ResultWrapper

interface ProductRepository {

    suspend fun getProducts(category: Int?): ResultWrapper<ProductListModel>
}