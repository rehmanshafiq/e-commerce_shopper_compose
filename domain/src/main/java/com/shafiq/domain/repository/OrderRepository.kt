package com.shafiq.domain.repository

import com.shafiq.domain.model.AddressDomainModel
import com.shafiq.domain.network.ResultWrapper

interface OrderRepository {
    suspend fun placeOrder(addressDomainModel: AddressDomainModel): ResultWrapper<Long>
}