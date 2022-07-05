package com.mindorks.framework.mvvm.ui.features.student.model

class StudentModel(
    val name: String,
    val age: Int? = 0,
    val address: String? = null,
    val experience: String? = null,
    val lastEducation: String? = null,
    val lastWorking: String? = null,
    val noHp: String? = null,
) {
    companion object {
        fun createList(numContacts: Int): ArrayList<StudentModel> {
            val guideModels = ArrayList<StudentModel>()
            for (i in 1..numContacts) {
//                guideModels.add(StudentModel("Person " + ++lastContactId, ""))
            }
            return guideModels
        }

        val listItem: List<StudentModel> = listOf(
            StudentModel("Arkana Qois", 25, "Desa Popoh ,Kec.Selopuro ,Kab.Blitar","Admin Aplikasi","SMA","Karyawan Swasta","08567490212"),
            StudentModel("Mahmudi", 32, "Desa Popoh ,Dsn.Trenceng,Kec.Selopuro ,Kab.Blitar","Tani","SMK","Buruh harian lepas","085645181970")
        )
    }
}
