package com.mindorks.framework.mvvm.ui.features.course.model

class StudentJoinModel(
    val name: String,
    val age: Int? = 0,
    val address: String? = null,
    val experience: String? = null,
    val lastEducation: String? = null,
    val lastWorking: String? = null,
    val noHp: String? = null,
    val reasonJoin: String? = null,
) {
    companion object {
        val listItem: List<StudentJoinModel> = listOf(
            StudentJoinModel("Arkana Qois", 25, "Desa Popoh ,Kec.Selopuro ,Kab.Blitar","Admin Aplikasi","SMA","Karyawan Swasta","08567490212"),
            StudentJoinModel("Mahmudi", 32, "Desa Popoh ,Dsn.Trenceng,Kec.Selopuro ,Kab.Blitar","Tani","SMK","Buruh harian lepas","085645181970")
        )
    }
}
