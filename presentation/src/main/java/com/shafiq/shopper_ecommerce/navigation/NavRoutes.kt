package com.shafiq.shopper_ecommerce.navigation

import com.shafiq.shopper_ecommerce.model.UIProductModel
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
object CartScreen

@Serializable
object ProfileScreen

@Serializable
data class ProductDetails(
    val product: UIProductModel
)