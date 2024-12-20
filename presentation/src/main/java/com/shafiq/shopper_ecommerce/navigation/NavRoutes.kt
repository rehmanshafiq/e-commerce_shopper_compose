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
object CartSummaryScreen

@Serializable
data class ProductDetails(
    val product: UIProductModel
)

@Serializable
data class UserAddressRoute(val userAddressRouteWrapper: UserAddressRouteWrapper)