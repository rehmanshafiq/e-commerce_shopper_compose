package com.shafiq.domain.usecase

import com.shafiq.domain.model.AddressDomainModel
import com.shafiq.domain.repository.OrderRepository

class PlaceOrderUseCase(private val orderRepository: OrderRepository) {
    suspend fun execute(addressDomainModel: AddressDomainModel) = orderRepository.placeOrder(addressDomainModel)
}