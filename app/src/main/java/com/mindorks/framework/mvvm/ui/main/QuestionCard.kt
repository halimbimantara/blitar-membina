/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
package com.mindorks.framework.mvvm.ui.main

import android.graphics.Color
import android.widget.Button
import android.widget.TextView
import com.androidnetworking.widget.ANImageView
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.core.data.model.others.QuestionCardData
import com.mindorks.placeholderview.annotations.*

/**
 * Created by amitshekhar on 08/07/17.
 */
@NonReusable
@Layout(R.layout.card_layout)
class QuestionCard(private val mQuestionCardData: QuestionCardData) {
    @View(R.id.btn_option_1)
    private val mOption1Button: Button? = null

    @View(R.id.btn_option_2)
    private val mOption2Button: Button? = null

    @View(R.id.btn_option_3)
    private val mOption3Button: Button? = null

    @View(R.id.iv_pic)
    private val mPicImageView: ANImageView? = null

    @View(R.id.tv_question_txt)
    private val mQuestionTextView: TextView? = null
    @Click(R.id.btn_option_1)
    fun onOption1Click() {
        showCorrectOptions()
    }

    @Click(R.id.btn_option_2)
    fun onOption2Click() {
        showCorrectOptions()
    }

    @Click(R.id.btn_option_3)
    fun onOption3Click() {
        showCorrectOptions()
    }

    @Resolve
    private fun onResolved() {
        mQuestionTextView!!.text = mQuestionCardData.question!!.questionText
        if (mQuestionCardData.mShowCorrectOptions) {
            showCorrectOptions()
        }
        for (i in 0..2) {
            var button: Button? = null
            when (i) {
                0 -> button = mOption1Button
                1 -> button = mOption2Button
                2 -> button = mOption3Button
            }
            if (button != null) {
                button.text = mQuestionCardData.options!![i]!!.optionText
            }
            if (mQuestionCardData.question!!.imgUrl != null) {
                mPicImageView!!.setImageUrl(mQuestionCardData.question!!.imgUrl)
            }
        }
    }

    private fun showCorrectOptions() {
        mQuestionCardData.mShowCorrectOptions = true
        for (i in 0..2) {
            val option = mQuestionCardData.options!![i]
            var button: Button? = null
            when (i) {
                0 -> button = mOption1Button
                1 -> button = mOption2Button
                2 -> button = mOption3Button
            }
            if (button != null) {
                if (option!!.isCorrect) {
                    button.setBackgroundColor(Color.GREEN)
                } else {
                    button.setBackgroundColor(Color.RED)
                }
            }
        }
    }
}