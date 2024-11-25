package com.shafiq.data.model

import com.shafiq.domain.model.Category
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDataModel(
    val id: Int,
    val image: String,
    val title: String
) {
    fun toCategory() = Category(
        id = id,
        image = image,
        title = title
    )
}