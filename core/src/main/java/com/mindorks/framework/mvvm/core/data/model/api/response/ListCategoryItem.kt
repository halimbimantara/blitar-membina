package com.mindorks.framework.mvvm.core.data.model.api.response


import com.google.gson.annotations.SerializedName

data class ListCategoryItem(
    var `data`: List<Data?>?,
    var pagination: Pagination?
) {
    data class Data(
        @SerializedName("category_extension")
        var categoryExtension: String?,
        @SerializedName("category_image")
        var categoryImage: String?,
        var color: String?,
        var description: String?,
        var id: Int?,
        @SerializedName("is_featured")
        var isFeatured: Int?,
        var name: String?,
        var services: Int?,
        var status: Int?
    )

    data class Pagination(
        var currentPage: Int?,
        var from: Int?,
        @SerializedName("next_page")
        var nextPage: Any?,
        @SerializedName("per_page")
        var perPage: Int?,
        @SerializedName("previous_page")
        var previousPage: Any?,
        var to: Int?,
        @SerializedName("total_items")
        var totalItems: Int?,
        var totalPages: Int?
    )
}