package com.shafiq.data.model.response

import com.shafiq.data.model.DataProductModel
import kotlinx.serialization.Serializable

@Serializable
data class ProductListResponse(
    val `data`: List<DataProductModel>,
    val msg: String
) {
    fun toProductList() = com.shafiq.domain.model.ProductListModel(
        products = `data`.map { it.toProduct() },
        msg = msg
    )
}