package com.mindorks.framework.mvvm.ui.features.survey

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mindorks.framework.mvvm.databinding.ContentSurveyBinding

class SurveyActivity : AppCompatActivity() {
    private lateinit var binding: ContentSurveyBinding

    companion object {
        fun newIntent(context: Context?, mTitle: String? = null, tipe: String? = null): Intent {
            return Intent(context, SurveyActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}