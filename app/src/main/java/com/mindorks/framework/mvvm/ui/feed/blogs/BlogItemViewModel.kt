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
package com.mindorks.framework.mvvm.ui.feed.blogs

import androidx.databinding.ObservableField
import com.mindorks.framework.mvvm.core.data.model.api.BlogResponse.Blog

/**
 * Created by amitshekhar on 10/07/17.
 */
class BlogItemViewModel(private val mBlog: Blog, val mListener: BlogItemViewModelListener) {
    @kotlin.jvm.JvmField
    val author: ObservableField<String?> = ObservableField(mBlog.author)

    @kotlin.jvm.JvmField
    val content: ObservableField<String?> = ObservableField(mBlog.description)

    @kotlin.jvm.JvmField
    val date: ObservableField<String?> = ObservableField(mBlog.date)

    @kotlin.jvm.JvmField
    val imageUrl: ObservableField<String?> = ObservableField(mBlog.coverImgUrl)

    @kotlin.jvm.JvmField
    val title: ObservableField<String?> = ObservableField(mBlog.title)
    fun onItemClick() {
        mListener.onItemClick(mBlog.blogUrl)
    }

    interface BlogItemViewModelListener {
        fun onItemClick(blogUrl: String?)
    }

}