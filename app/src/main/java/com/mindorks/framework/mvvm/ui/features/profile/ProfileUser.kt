package com.mindorks.framework.mvvm.ui.features.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.databinding.ContentProfilePembinaBinding

/**
 * Detail Pelamar/Calon Peserta
 */
class ProfileUser : AppCompatActivity() {
    private val binding: ContentProfilePembinaBinding by viewBinding()

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, ProfileUser::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_profile_pembina)
        setContentView(binding.root)
    }
}