package com.shafiq.domain.repository

import com.shafiq.domain.model.CategoriesListModel
import com.shafiq.domain.network.ResultWrapper

interface CategoryRepository {
    suspend fun getCategories(): ResultWrapper<CategoriesListModel>
}