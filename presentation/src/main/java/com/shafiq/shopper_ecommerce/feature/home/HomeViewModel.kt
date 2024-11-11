package com.shafiq.shopper_ecommerce.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shafiq.domain.model.Product
import com.shafiq.domain.network.ResultWrapper
import com.shafiq.domain.usecase.GetCategoriesUseCase
import com.shafiq.domain.usecase.GetProductUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<HomeScreenUIEvents>(HomeScreenUIEvents.Loading)

    val uiState = _uiState.asStateFlow()

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            _uiState.value = HomeScreenUIEvents.Loading
            val featured = getProducts(category = "electronics")
            val popularProduct = getProducts(category = "jewelery")
            val categories = getCategory()
            if (featured.isEmpty() && popularProduct.isEmpty() && categories.isNotEmpty()) {
                _uiState.value = HomeScreenUIEvents.Error("Failed to load properly")
                return@launch
            }
            _uiState.value = HomeScreenUIEvents.Success(featured, popularProduct, categories)
        }
    }

    private suspend fun getProducts(category: String?) : List<Product> {
        getProductUseCase.execute(category).let { result ->
            when(result) {
                is ResultWrapper.Success -> {
                    return result.value
                }
                is ResultWrapper.Failure -> {
                    return emptyList()
                }
            }
        }
    }

    private suspend fun getCategory(): List<String> {
        getCategoriesUseCase.execute().let { result ->
            when(result) {
                is ResultWrapper.Success -> {
                    return result.value
                }
                is ResultWrapper.Failure -> {
                    return emptyList()
                }
            }
        }
    }
}

sealed class HomeScreenUIEvents {
    data object Loading : HomeScreenUIEvents()
    data class Success(
        val featured: List<Product>,
        val popularProduct: List<Product>,
        val categories: List<String> = emptyList()
    ): HomeScreenUIEvents()
    data class Error(val message: String): HomeScreenUIEvents()
}