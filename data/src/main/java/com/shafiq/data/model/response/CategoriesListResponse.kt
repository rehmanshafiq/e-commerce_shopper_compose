package com.shafiq.data.model.response

import com.shafiq.data.model.CategoryDataModel
import com.shafiq.domain.model.CategoriesListModel
import kotlinx.serialization.Serializable

@Serializable
data class CategoriesListResponse(
    val `data`: List<CategoryDataModel>,
    val msg: String
) {
    fun toCategoriesList() = CategoriesListModel(
        categories = `data`.map { it.toCategory() },
        msg = msg
    )
}