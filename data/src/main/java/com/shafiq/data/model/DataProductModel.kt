package com.shafiq.data.model

import com.shafiq.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
class DataProductModel (
    val id: Int,
    val title: String,
    val price: Double,
    val category: String,
    val description: String,
    val image: String
) {
    fun toProduct() = Product(
        id = id,
        title = title,
        price = price,
        category = category,
        description = description,
        image = image
    )
}