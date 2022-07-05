package com.mindorks.framework.mvvm.core.data.model.api.service


import com.google.gson.annotations.SerializedName

data class ServiceResponse(
    var `data`: List<Data?>?,
    var max: Int?,
    var min: Int?,
    var pagination: Pagination?
) {
    data class Data(
        @SerializedName("attchment_extension")
        var attchmentExtension: Boolean?,
        var attchments: List<String?>?,
        @SerializedName("attchments_array")
        var attchmentsArray: List<AttchmentsArray?>?,
        @SerializedName("category_id")
        var categoryId: Int?,
        @SerializedName("category_name")
        var categoryName: String?,
        @SerializedName("city_id")
        var cityId: Int?,
        var description: String?,
        var discount: Int?,
        var duration: String?,
        var id: Int?,
        @SerializedName("is_favourite")
        var isFavourite: Int?,
        @SerializedName("is_featured")
        var isFeatured: Int?,
        var name: String?,
        var price: Int?,
        @SerializedName("price_format")
        var priceFormat: String?,
        @SerializedName("provider_id")
        var providerId: Int?,
        @SerializedName("provider_image")
        var providerImage: String?,
        @SerializedName("provider_name")
        var providerName: String?,
        @SerializedName("service_address_mapping")
        var serviceAddressMapping: List<Any?>?,
        var status: Int?,
        @SerializedName("subcategory_id")
        var subcategoryId: Any?,
        @SerializedName("subcategory_name")
        var subcategoryName: Any?,
        @SerializedName("total_rating")
        var totalRating: Int?,
        @SerializedName("total_review")
        var totalReview: Int?,
        var type: String?
    ) {
        data class AttchmentsArray(
            var id: Int?,
            var url: String?
        )
    }

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