package com.shafiq.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class PlaceOrderResponse(
    val `data`: OrderD,
    val msg: String
)