package com.mindorks.framework.mvvm.ui.features.course.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.databinding.ContentFrameFullBinding
import com.mindorks.framework.mvvm.ui.features.course.fragment.ListJadwalFragment

class ListJadwal : AppCompatActivity() {
    private lateinit var binding: ContentFrameFullBinding

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
            return Intent(context, ListJadwal::class.java).apply {
                putExtra(EXTRA_TITLE, mTitle)
                putExtra(EXTRA_TIPE, tipe)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentFrameFullBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager
            // 3
            .beginTransaction()
            // 4
            .add(R.id.content_fragment_view, ListJadwalFragment(), "jadwal")
            // 5
            .commit()
        title = titleCourse
    }
}