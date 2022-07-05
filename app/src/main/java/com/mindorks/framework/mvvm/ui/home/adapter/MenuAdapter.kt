package com.mindorks.framework.mvvm.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.ui.features.course.activity.ListAllLowonganActivity
import com.mindorks.framework.mvvm.ui.features.course.activity.ListJadwal
import com.mindorks.framework.mvvm.ui.features.pembina.activity.RewardDetailActivity
import com.mindorks.framework.mvvm.ui.features.profile.ProfileUser
import com.mindorks.framework.mvvm.ui.features.survey.CallCenterActivity
import com.mindorks.framework.mvvm.ui.features.survey.SurveyActivity
import com.mindorks.framework.mvvm.ui.home.models.MenuGrid
import com.mindorks.framework.mvvm.ui.main.MainActivity

class MenuAdapter : BaseAdapter {
    var logoList = ArrayList<MenuGrid>()
    var context: Context? = null

    constructor(logoList: ArrayList<MenuGrid>, context: Context?) : super() {
        this.logoList = logoList
        this.context = context
    }

    override fun getCount(): Int {
        return logoList.size
    }

    override fun getItem(position: Int): Any {
        return logoList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val menu = this.logoList[position]

        var inflator =
            context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater//get layout from gridview_berita.xml
        var logoView = inflator.inflate(R.layout.item_menu_content, null)

        logoView.findViewById<ImageView>(R.id.ImgMenu).setImageResource(menu.image!!)
        logoView.findViewById<TextView>(R.id.TvTitle).text =
            menu.name!!//in case if you want to visit new activity after click the menu
        logoView.setOnClickListener {
            when (position) {

                0 -> {
                    (context as MainActivity).startActivity(
                        ListAllLowonganActivity.newIntent(
                            context,
                            "List Lowongan / Magang"
                        )
                    )
                }
                1 -> {
                    (context as MainActivity).startActivity(
                        ListJadwal.newIntent(
                            context,
                            "List Jadwal Magang"
                        )
                    )

                }

                3 -> {
                    (context as MainActivity).startActivity(
                        Intent(
                            context,
                            ProfileUser::class.java
                        )
                    )

                }
                5 -> {
                    (context as MainActivity).startActivity(CallCenterActivity.newIntent(context))
                }
                6 -> {
                    (context as MainActivity).startActivity(SurveyActivity.newIntent(context))
                }
                7 -> {
                    (context as MainActivity).startActivity(RewardDetailActivity.newIntent(context))
                }
            }
        }

        return logoView
    }
}
