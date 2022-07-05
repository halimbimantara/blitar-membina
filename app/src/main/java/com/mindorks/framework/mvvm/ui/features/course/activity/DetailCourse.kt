package com.mindorks.framework.mvvm.ui.features.course.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.databinding.ActicityCourseDetailBinding
import com.mindorks.framework.mvvm.ui.features.course.model.KurikulumModel
import com.mindorks.framework.mvvm.utils.PreferenceUtils
import com.mindorks.framework.mvvm.ui.features.course.adapter.AdapterKurikulum
import com.mindorks.framework.mvvm.ui.features.profile.ProfileUser
import org.hawlastudio.binaahli.utils.ext.gone
import org.hawlastudio.binaahli.utils.ext.visible

class DetailCourse : AppCompatActivity() {
    private lateinit var binding: ActicityCourseDetailBinding

    private val titleCourse by lazy {
        intent.getStringExtra(EXTRA_TITLE)
    }
    private val accessTipe by lazy {
        intent.getStringExtra(EXTRA_TIPE)
    }


    companion object {
        const val EXTRA_TITLE = "EXTRA_TITLE"

        /**
         * tipe student,pembina
         */
        const val EXTRA_TIPE = "EXTRA_TIPE"

        fun newIntent(context: Context?, mTitle: String, tipe: String? = null): Intent {
            return Intent(context, DetailCourse::class.java).apply {
                putExtra(EXTRA_TITLE, mTitle)
                putExtra(EXTRA_TIPE, tipe)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActicityCourseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = titleCourse

        val mAdapter = AdapterKurikulum(KurikulumModel.listItem)
        binding.rvCuricullum.adapter = mAdapter
        binding.rvCuricullum.layoutManager = LinearLayoutManager(this)
        binding.textViewPembinaDetail.setOnClickListener {
            startActivity(ProfileUser.newIntent(this))
        }
        when (accessTipe) {
            PreferenceUtils.EXTRA_TYPE_ROLE_PELAMAR -> {
                binding.BtnApply.visible()
                actionApply()
            }

            PreferenceUtils.EXTRA_TYPE_ROLE_PEMBINA -> {
                binding.BtnApply.gone()
            }
        }
    }

    private fun actionApply() {
        viewListStudent()
    }

    private fun viewListStudent() {
        binding.BtnApply.setOnClickListener {
            MaterialDialog(this).show {
               title(text = "Apakah anda yakin mengajukan lamaran ini ?")
                customView(R.layout.dialog_content_enter_reason_apply)
                positiveButton(R.string.submit)
                negativeButton(R.string.batal)
            }
        }
    }
}