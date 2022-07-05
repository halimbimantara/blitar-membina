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
package com.mindorks.framework.mvvm.ui.feed.opensource

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.framework.mvvm.databinding.ItemOpenSourceEmptyViewBinding
import com.mindorks.framework.mvvm.databinding.ItemOpenSourceViewBinding
import com.mindorks.framework.mvvm.ui.base.BaseViewHolder
import com.mindorks.framework.mvvm.ui.feed.opensource.OpenSourceEmptyItemViewModel.OpenSourceEmptyItemViewModelListener
import com.mindorks.framework.mvvm.core.utils.AppLogger

/**
 * Created by amitshekhar on 10/07/17.
 */
class OpenSourceAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    private val mOpenSourceResponseList: MutableList<OpenSourceItemViewModel>
    private var mListener: OpenSourceAdapterListener? = null
    override fun getItemCount(): Int {
        return if (!mOpenSourceResponseList.isEmpty()) {
            mOpenSourceResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (!mOpenSourceResponseList.isEmpty()) {
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
                val openSourceViewBinding =
                    ItemOpenSourceViewBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                OpenSourceViewHolder(openSourceViewBinding)
            }
            VIEW_TYPE_EMPTY -> {
                val emptyViewBinding =
                    ItemOpenSourceEmptyViewBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                EmptyViewHolder(
                    emptyViewBinding
                )
            }
            else -> {
                val emptyViewBinding =
                    ItemOpenSourceEmptyViewBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                EmptyViewHolder(
                    emptyViewBinding
                )
            }
        }
    }

    fun addItems(repoList: List<OpenSourceItemViewModel>?) {
        mOpenSourceResponseList.addAll(repoList!!)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mOpenSourceResponseList.clear()
    }

    fun setListener(listener: OpenSourceAdapterListener?) {
        mListener = listener
    }

    interface OpenSourceAdapterListener {
        fun onRetryClick()
    }

    inner class EmptyViewHolder(private val mBinding: ItemOpenSourceEmptyViewBinding) :
        BaseViewHolder(
            mBinding.root
        ), OpenSourceEmptyItemViewModelListener {
        override fun onBind(position: Int) {
            val emptyItemViewModel = OpenSourceEmptyItemViewModel(this)
            mBinding.viewModel = emptyItemViewModel
        }

        override fun onRetryClick() {
            mListener!!.onRetryClick()
        }
    }

    inner class OpenSourceViewHolder(private val mBinding: ItemOpenSourceViewBinding) :
        BaseViewHolder(
            mBinding.root
        ), View.OnClickListener {
        override fun onBind(position: Int) {
            val mOpenSourceItemViewModel = mOpenSourceResponseList[position]
            mBinding.viewModel = mOpenSourceItemViewModel

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings()
        }

        override fun onClick(view: View) {
            if (mOpenSourceResponseList[0].projectUrl.get() != null) {
                try {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse(mOpenSourceResponseList[0].projectUrl.get())
                    itemView.context.startActivity(intent)
                } catch (e: Exception) {
                    AppLogger.d("url error")
                }
            }
        }
    }

    companion object {
        const val VIEW_TYPE_EMPTY = 0
        const val VIEW_TYPE_NORMAL = 1
    }

    init {
        mOpenSourceResponseList = ArrayList()
    }
}