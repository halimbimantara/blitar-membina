package com.mindorks.framework.mvvm.core.data.model.api.response


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    var `data`: Data?,
    var message: String?
) {
    data class Data(
        @SerializedName("api_token")
        var apiToken: String?,
        @SerializedName("created_at")
        var createdAt: String?,
        @SerializedName("display_name")
        var displayName: String?,
        var email: String?,
        @SerializedName("first_name")
        var firstName: String?,
        var gender: String?,
        var id: Int?,
        @SerializedName("last_name")
        var lastName: String?,
        var roles: List<Role?>?,
        @SerializedName("updated_at")
        var updatedAt: String?,
        @SerializedName("user_type")
        var userType: String?,
        var username: String?
    ) {
        data class Role(
            @SerializedName("created_at")
            var createdAt: String?,
            @SerializedName("guard_name")
            var guardName: String?,
            var id: Int?,
            var name: String?,
            var pivot: Pivot?,
            var status: Int?,
            @SerializedName("updated_at")
            var updatedAt: String?
        ) {
            data class Pivot(
                @SerializedName("model_id")
                var modelId: Int?,
                @SerializedName("model_type")
                var modelType: String?,
                @SerializedName("role_id")
                var roleId: Int?
            )
        }
    }
}