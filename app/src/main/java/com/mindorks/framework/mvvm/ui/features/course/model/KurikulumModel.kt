package com.mindorks.framework.mvvm.ui.features.course.model

class KurikulumModel(
    val name: String,
    val description: String? = null,
) {
    companion object {
        val listItem: List<KurikulumModel> = listOf(
            KurikulumModel("Teknik Dasar Pengelasan", description = "Mengetahui jenis tipe mesin las dan karakteristiknya" ),
            KurikulumModel("Teknik Dasar Pengelasan ", description = "Material Pengelasan masing-masing mesin las" ),
           )

        val listItem2: List<KurikulumModel> = listOf(
            KurikulumModel("Teknik Dasar Pengelasan | Pertemuan ke 1", description = "Mengetahui jenis tipe mesin las dan karakteristiknya" ),
            KurikulumModel("Teknik Dasar Pengelasan | Pertemuan ke 2", description = "Material Pengelasan masing-masing mesin las" ),
        )


        val listItemJadwal: List<KurikulumModel> = listOf(
            KurikulumModel("Kamis & Jumat | 13:30 - 15:00", description = "Pelatihan pengelasan dan pembuatan pagar besi dan tralis" ),
            KurikulumModel("Sabtu & Minggu | 14:00 - 16:00", description = "Material Pengelasan masing-masing mesin las" ),
        )
    }
}
