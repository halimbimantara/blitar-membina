package com.mindorks.framework.mvvm.ui.home.models

import com.mindorks.framework.mvvm.R


class CourseModel(
    val title: String,
    val LinkUrl: String? = null,
    val imagesAsset: String? = null,
    val description: String? = null,
    val imgDrawable: Int? = null,
    val status: String? = null,
) {

    companion object {
        const val extra_status_pending ="pending"
        const val extra_status_reject ="reject"
        const val extra_status_approve ="approve"
        const val extra_status_sertifikat ="sertifikat"
        private var lastContactId = 0
        fun createContactsList(numContacts: Int): ArrayList<CourseModel> {
            val guideModels = ArrayList<CourseModel>()
            for (i in 1..numContacts) {
                guideModels.add(CourseModel("Person " + ++lastContactId, ""))
            }
            return guideModels
        }

        val listGuide: List<CourseModel> = listOf(
            CourseModel(
                "Peternakan | Blitar", description = "Pelatihan budidaya ternak ayam sayur",
                imgDrawable = R.drawable.img_loker_1
            ),
            CourseModel(
                "Pengelasan dan mekanik | Blitar",
                description = "Pelatihan pengelasan dan pembuatan pagar besi dan tralis",
                imgDrawable = R.drawable.img_loker_2
            ),
        )

        val listNotif: List<CourseModel> = listOf(
            CourseModel(
                "Peternakan | Blitar", description = "Pelatihan budidaya ternak ayam sayur",
                imgDrawable = R.drawable.img_loker_1, status = extra_status_reject
            ),
            CourseModel(
                "Pengelasan dan mekanik | Blitar",
                description = "Pelatihan pengelasan dan pembuatan pagar besi dan tralis",
                imgDrawable = R.drawable.img_loker_2,
                status = extra_status_approve
            ),

            CourseModel(
                "Pelatihan Mesin Bubut | Blitar",
                description = "Pelatihan dasar menggunakan mesin bubut",
                imgDrawable = R.drawable.img_sample_bubut,
                status = extra_status_sertifikat
            ),
        )
    }
}
