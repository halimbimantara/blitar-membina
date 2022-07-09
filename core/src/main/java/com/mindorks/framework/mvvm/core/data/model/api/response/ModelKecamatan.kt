package com.mindorks.framework.mvvm.core.data.model.api.response


import com.google.gson.annotations.SerializedName

data class ModelKecamatan(
    var `data`: List<Data?>?
) {
    data class Data(
        @SerializedName("city_id")
        var cityId: Int?,
        var id: Int?,
        var nama: String?
    )
}