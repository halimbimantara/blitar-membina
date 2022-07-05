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
package com.mindorks.framework.mvvm.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mindorks.framework.mvvm.core.data.model.api.BlogResponse.Blog
import com.mindorks.framework.mvvm.core.data.model.others.QuestionCardData
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogAdapter
import com.mindorks.framework.mvvm.ui.feed.opensource.OpenSourceAdapter
import com.mindorks.framework.mvvm.ui.feed.opensource.OpenSourceItemViewModel
import com.mindorks.framework.mvvm.ui.main.MainViewModel
import com.mindorks.framework.mvvm.ui.main.QuestionCard
import com.mindorks.placeholderview.SwipePlaceHolderView

/**
 * Created by amitshekhar on 11/07/17.
 */
object BindingUtils {
    @kotlin.jvm.JvmStatic
    @BindingAdapter("adapter")
    fun addBlogItems(recyclerView: RecyclerView, blogs: List<Blog>?) {
        val adapter = recyclerView.adapter as BlogAdapter?
        if (adapter != null) {
            adapter.clearItems()
            adapter.addItems(blogs)
        }
    }

    @kotlin.jvm.JvmStatic
    @BindingAdapter("adapter")
    fun addOpenSourceItems(
        recyclerView: RecyclerView,
        openSourceItems: List<OpenSourceItemViewModel>?
    ) {
        val adapter = recyclerView.adapter as OpenSourceAdapter?
        if (adapter != null) {
            adapter.clearItems()
            adapter.addItems(openSourceItems)
        }
    }

    @kotlin.jvm.JvmStatic
    @BindingAdapter("adapter", "action")
    fun addQuestionItems(
        mCardsContainerView: SwipePlaceHolderView,
        mQuestionList: List<QuestionCardData?>?,
        mAction: Int
    ) {
        if (mAction == MainViewModel.Companion.ACTION_ADD_ALL) {
            if (mQuestionList != null) {
                mCardsContainerView.removeAllViews()
                for (question in mQuestionList) {
                    if (question?.options != null && question.options!!.size == 3) {
                        mCardsContainerView.addView(QuestionCard(question))
                    }
                }
                ViewAnimationUtils.scaleAnimateView(mCardsContainerView)
            }
        }
    }

    @kotlin.jvm.JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView, url: String?) {
        val context = imageView.context
        Glide.with(context).load(url).into(imageView)
    }
}