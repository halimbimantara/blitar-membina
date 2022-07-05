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

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.framework.mvvm.core.data.model.api.BlogResponse.Blog
import com.mindorks.framework.mvvm.databinding.ItemBlogEmptyViewBinding
import com.mindorks.framework.mvvm.databinding.ItemBlogViewBinding
import com.mindorks.framework.mvvm.ui.base.BaseViewHolder
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogEmptyItemViewModel.BlogEmptyItemViewModelListener
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogItemViewModel.BlogItemViewModelListener
import com.mindorks.framework.mvvm.core.utils.AppLogger

/**
 * Created by amitshekhar on 10/07/17.
 */
class BlogAdapter(private val mBlogResponseList: MutableList<Blog>?) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private var mListener: BlogAdapterListener? = null
    override fun getItemCount(): Int {
        return if (mBlogResponseList != null && mBlogResponseList.size > 0) {
            mBlogResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mBlogResponseList != null && !mBlogResponseList.isEmpty()) {
            VIEW_TYPE_NORMAL
        } else {
            VIEW_TYPE_EMPTY
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> {
                val blogViewBinding =
                    ItemBlogViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                BlogViewHolder(blogViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding =
                    ItemBlogEmptyViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                EmptyViewHolder(emptyViewBinding)
            }
            else -> {
                val emptyViewBinding =
                    ItemBlogEmptyViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                EmptyViewHolder(emptyViewBinding)
            }
        }
    }

    fun addItems(blogList: List<Blog>?) {
        mBlogResponseList!!.addAll(blogList!!)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mBlogResponseList!!.clear()
    }

    fun setListener(listener: BlogAdapterListener?) {
        mListener = listener
    }

    interface BlogAdapterListener {
        fun onRetryClick()
    }

    inner class BlogViewHolder(private val mBinding: ItemBlogViewBinding) : BaseViewHolder(
        mBinding.root
    ), BlogItemViewModelListener {
        private var mBlogItemViewModel: BlogItemViewModel? = null
        override fun onBind(position: Int) {
            val blog = mBlogResponseList!![position]
            mBlogItemViewModel = BlogItemViewModel(blog, this)
            mBinding.viewModel = mBlogItemViewModel

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()
        }

        override fun onItemClick(blogUrl: String?) {
            if (blogUrl != null) {
                try {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse(blogUrl)
                    itemView.context.startActivity(intent)
                } catch (e: Exception) {
                    AppLogger.d("url error")
                }
            }
        }
    }

    inner class EmptyViewHolder(private val mBinding: ItemBlogEmptyViewBinding) : BaseViewHolder(
        mBinding.root
    ), BlogEmptyItemViewModelListener {
        override fun onBind(position: Int) {
            val emptyItemViewModel = BlogEmptyItemViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_NORMAL = 1
    }
}