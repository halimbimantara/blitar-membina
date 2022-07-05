package com.mindorks.framework.mvvm.ui.home.models

import com.mindorks.framework.mvvm.R

class MenuGrid {
    var name: String? = null
    var image: Int? = null
    var url: String? = null

    constructor(name: String, image: Int, url: String) {
        this.name = name
        this.image = image
        this.url = url
    }
    companion object{
        val listMenu: List<MenuGrid> = listOf(
            MenuGrid(name = "Lowongan", image = R.drawable.ico_menu_lowongan,""),
            MenuGrid(name = "Jadwal", image = R.drawable.ico_menu_jadwal,""),
            MenuGrid(name = "Laporan", image = R.drawable.ico_menu_laporan,""),
            MenuGrid(name = "Profil", image = R.drawable.ico_menu_user,""),
            MenuGrid(name = "Sertifikat", image = R.drawable.ico_menu_sertifikat,""),
            MenuGrid(name = "Hubungi Admin", image = R.drawable.ico_menu_support,""),
            MenuGrid(name = "Survei", image = R.drawable.ico_menu_survei,""),
        )
        val listMenuPembina: List<MenuGrid> = listOf(
            MenuGrid(name = "Lowongan", image = R.drawable.ico_menu_lowongan, ""),
            MenuGrid(name = "Jadwal", image = R.drawable.ico_menu_jadwal, ""),
            MenuGrid(name = "Laporan", image = R.drawable.ico_menu_laporan, ""),
            MenuGrid(name = "Profil", image = R.drawable.ico_menu_user, ""),
            MenuGrid(name = "Sertifikat", image = R.drawable.ico_menu_sertifikat, ""),
            MenuGrid(name = "Hubungi Admin", image = R.drawable.ico_menu_support, ""),
            MenuGrid(name = "Survei", image = R.drawable.ico_menu_survei, ""),
            MenuGrid(name = "Reward", image = R.drawable.ico_menu_reward, ""),
        )
    }
}
