package com.shafiq.shopper_ecommerce.ui.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shafiq.domain.model.CartItemModel
import com.shafiq.domain.network.ResultWrapper
import com.shafiq.domain.usecase.GetCartUseCase
import com.shafiq.domain.usecase.UpdateQuantityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartUseCase: GetCartUseCase,
    private val updateQuantityUseCase: UpdateQuantityUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CartEvent>(CartEvent.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getCart()
    }

    fun getCart() {
        viewModelScope.launch {
            _uiState.value = CartEvent.Loading
            cartUseCase.execute().let { result ->
                when(result) {
                    is ResultWrapper.Success -> {
                        _uiState.value = CartEvent.Success(result.value.data)
                    }
                    is ResultWrapper.Failure -> {
                        _uiState.value = CartEvent.Error("Something went wrong")
                    }
                }
            }
        }
    }

    fun incrementQuantity(cartItem: CartItemModel) {
        updateQuantity(cartItem.copy(quantity = cartItem.quantity + 1))
    }

    fun decrementQuantity(cartItem: CartItemModel) {
        updateQuantity(cartItem.copy(quantity = cartItem.quantity - 1))
    }

    private fun updateQuantity(cartItem: CartItemModel) {
        viewModelScope.launch {
            _uiState.value = CartEvent.Loading
            val result = updateQuantityUseCase.execute(cartItem)
            when(result) {
                is ResultWrapper.Failure -> {
                   _uiState.value = CartEvent.Error("Something went wrong!")
                }
                is ResultWrapper.Success -> {
                    _uiState.value = CartEvent.Success(result.value.data)
                }
            }
        }
    }

    fun removeItem(cartItem: CartItemModel) {

    }

}

sealed class CartEvent {
    data object Loading: CartEvent()
    data class Success(val message: List<CartItemModel>): CartEvent()
    data class Error(val message: String): CartEvent()
}