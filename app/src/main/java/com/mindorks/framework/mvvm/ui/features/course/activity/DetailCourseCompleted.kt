package com.mindorks.framework.mvvm.ui.features.course.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.databinding.ContentListCompleteBinding
import com.mindorks.framework.mvvm.ui.features.certificate.DetailCertificate
import com.mindorks.framework.mvvm.ui.features.course.adapter.AdapterKurikulum
import com.mindorks.framework.mvvm.ui.features.course.model.KurikulumModel
import com.mindorks.framework.mvvm.utils.PreferenceUtils

class DetailCourseCompleted : AppCompatActivity() {
    private lateinit var binding: ContentListCompleteBinding

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
            return Intent(context, DetailCourseCompleted::class.java).apply {
                putExtra(EXTRA_TITLE, mTitle)
                putExtra(EXTRA_TIPE, tipe)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentListCompleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = titleCourse

        when (accessTipe) {
            PreferenceUtils.EXTRA_TYPE_ROLE_PELAMAR -> {
                actionApply()
            }

            PreferenceUtils.EXTRA_TYPE_ROLE_PEMBINA -> {
                viewListStudent()
                actionApply()
            }
        }
    }

    private fun actionApply() {
        viewListStudent()
        binding.btnCetak.setOnClickListener {
            startActivity(
                DetailCertificate.newIntent(this, getString(R.string.label_sertifikat), "")
            )
        }
    }

    private fun viewListStudent() {
        val mAdapter = AdapterKurikulum(KurikulumModel.listItem2)
        binding.rvListKurikulumReport.adapter = mAdapter
        binding.rvListKurikulumReport.layoutManager = LinearLayoutManager(this)
    }
}