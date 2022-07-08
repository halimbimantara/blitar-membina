package com.mindorks.framework.mvvm.core.data.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ApiBaseResponse {
    @Expose
    @SerializedName("message")
    var messages: String? = null

}