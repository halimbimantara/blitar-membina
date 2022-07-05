package com.mindorks.framework.mvvm.ui.features.pembina.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mindorks.framework.mvvm.databinding.ContentProfilePembinaRewardBinding

class RewardDetailActivity : AppCompatActivity() {
    private lateinit var binding: ContentProfilePembinaRewardBinding

    companion object {
        fun newIntent(context: Context?, mTitle: String? = null, tipe: String? = null): Intent {
            return Intent(context, RewardDetailActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentProfilePembinaRewardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}