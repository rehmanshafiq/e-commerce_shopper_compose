package com.shafiq.data.repository

import com.shafiq.domain.network.NetworkService
import com.shafiq.domain.network.ResultWrapper
import com.shafiq.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    private val networkService: NetworkService
) : CategoryRepository {

    override suspend fun getCategories(): ResultWrapper<List<String>> {
        return networkService.getCategories()
    }
}