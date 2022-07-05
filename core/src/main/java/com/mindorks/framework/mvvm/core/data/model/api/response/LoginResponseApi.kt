package com.mindorks.framework.mvvm.core.data.model.api.response


import com.google.gson.annotations.SerializedName

data class LoginResponseApi(
    var `data`: Data?
) {
    data class Data(
        var address: Any?,
        @SerializedName("api_token")
        var apiToken: String?,
        @SerializedName("city_id")
        var cityId: Any?,
        @SerializedName("contact_number")
        var contactNumber: Any?,
        @SerializedName("country_id")
        var countryId: Any?,
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("deleted_at")
        var deletedAt: Any?,
        @SerializedName("display_name")
        var displayName: String?,
        var email: String?,
        @SerializedName("email_verified_at")
        var emailVerifiedAt: Any?,
        @SerializedName("first_name")
        var firstName: String?,
        @SerializedName("handymantype_id")
        var handymantypeId: Any?,
        var id: Int?,
        @SerializedName("is_featured")
        var isFeatured: Int?,
        @SerializedName("is_subscribe")
        var isSubscribe: Int?,
        @SerializedName("is_verify_provider")
        var isVerifyProvider: Int?,
        @SerializedName("last_name")
        var lastName: String?,
        @SerializedName("last_notification_seen")
        var lastNotificationSeen: Any?,
        @SerializedName("login_type")
        var loginType: Any?,
        @SerializedName("player_id")
        var playerId: Any?,
        @SerializedName("pm_last_four")
        var pmLastFour: Any?,
        @SerializedName("pm_type")
        var pmType: Any?,
        @SerializedName("profile_image")
        var profileImage: String?,
        @SerializedName("provider_id")
        var providerId: Any?,
        @SerializedName("providertype_id")
        var providertypeId: Any?,
        @SerializedName("service_address_id")
        var serviceAddressId: Any?,
        @SerializedName("social_image")
        var socialImage: Any?,
        @SerializedName("state_id")
        var stateId: Any?,
        var status: Int?,
        @SerializedName("stripe_id")
        var stripeId: Any?,
        @SerializedName("time_zone")
        var timeZone: String?,
        @SerializedName("trial_ends_at")
        var trialEndsAt: Any?,
        var uid: Any?,
        @SerializedName("updated_at")
        var updatedAt: String?,
        @SerializedName("user_role")
        var userRole: List<String?>?,
        @SerializedName("user_type")
        var userType: String?,
        var username: String?
    )
}