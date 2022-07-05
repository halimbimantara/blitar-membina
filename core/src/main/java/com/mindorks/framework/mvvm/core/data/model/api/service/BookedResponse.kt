package com.mindorks.framework.mvvm.core.data.model.api.service


import com.google.gson.annotations.SerializedName

data class BookedResponse(
    var `data`: List<Data?>?,
    var pagination: Pagination?
) {
    data class Data(
        var address: String?,
        @SerializedName("booking_address_id")
        var bookingAddressId: Any?,
        @SerializedName("coupon_data")
        var couponData: Any?,
        @SerializedName("customer_id")
        var customerId: Int?,
        @SerializedName("customer_name")
        var customerName: String?,
        var date: String?,
        var description: Any?,
        var discount: Int?,
        @SerializedName("duration_diff")
        var durationDiff: String?,
        @SerializedName("duration_diff_hour")
        var durationDiffHour: Any?,
        var handyman: List<Any?>?,
        var id: Int?,
        @SerializedName("payment_id")
        var paymentId: Any?,
        @SerializedName("payment_method")
        var paymentMethod: Any?,
        @SerializedName("payment_status")
        var paymentStatus: Any?,
        var price: Int?,
        @SerializedName("provider_id")
        var providerId: Int?,
        @SerializedName("provider_name")
        var providerName: String?,
        var quantity: Int?,
        @SerializedName("service_attchments")
        var serviceAttchments: List<String?>?,
        @SerializedName("service_id")
        var serviceId: Int?,
        @SerializedName("service_name")
        var serviceName: String?,
        var status: String?,
        @SerializedName("status_label")
        var statusLabel: String?,
        var taxes: Any?,
        @SerializedName("total_amount")
        var totalAmount: Int?,
        @SerializedName("total_rating")
        var totalRating: Int?,
        var type: String?
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