package com.shafiq.domain.usecase

import com.shafiq.domain.repository.CategoryRepository

class GetCategoriesUseCase(
    private val repository: CategoryRepository
) {

    suspend fun execute() = repository.getCategories()
}