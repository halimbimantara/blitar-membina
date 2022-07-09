package com.mindorks.framework.mvvm.ui.features.course.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.databinding.DetailContentAppliedCourseBinding
import com.mindorks.framework.mvvm.utils.PreferenceUtils
import com.mindorks.framework.mvvm.utils.ext.gone
import com.mindorks.framework.mvvm.utils.ext.visible

class DetailCourseApplied : AppCompatActivity() {
    private lateinit var binding: DetailContentAppliedCourseBinding

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
            return Intent(context, DetailCourseApplied::class.java).apply {
                putExtra(EXTRA_TITLE, mTitle)
                putExtra(EXTRA_TIPE, tipe)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailContentAppliedCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = titleCourse

        when (accessTipe) {
            PreferenceUtils.EXTRA_TYPE_ROLE_PELAMAR -> {
                binding.button3.gone()
                binding.button2.visible()
            }

            PreferenceUtils.EXTRA_TYPE_ROLE_PEMBINA -> {
                //
                binding.button2.gone()
                binding.button3.visible()
                binding.button3.setOnClickListener {
                    MaterialDialog(this).show {
                        customView(R.layout.content_set_penilaian)
                        negativeButton(R.string.batal)
                    }
                }
            }
        }
    }

}