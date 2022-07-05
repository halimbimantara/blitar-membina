package com.mindorks.framework.mvvm.ui.features.survey

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mindorks.framework.mvvm.databinding.ActivityContactAdminBinding

class CallCenterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactAdminBinding

    companion object {
        fun newIntent(context: Context?, mTitle: String? = null, tipe: String? = null): Intent {
            return Intent(context, CallCenterActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}