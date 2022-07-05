package com.mindorks.framework.mvvm.ui.features.course.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mindorks.framework.mvvm.databinding.ContentAppliedCourseBinding

class ListStudentApplyCourse : AppCompatActivity() {
    private lateinit var binding: ContentAppliedCourseBinding

    private val titleCourse by lazy {
        intent.getStringExtra(EXTRA_TITLE)
    }

    companion object {
        const val EXTRA_TITLE = "EXTRA_TITLE"

        fun newIntent(context: Context?, mTitle: String?=null, tipe: String? = null): Intent {
            return Intent(context, ListStudentApplyCourse::class.java).apply {
                putExtra(EXTRA_TITLE, mTitle)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentAppliedCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}