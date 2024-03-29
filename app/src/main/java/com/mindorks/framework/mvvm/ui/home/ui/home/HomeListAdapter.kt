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
package com.mindorks.framework.mvvm.ui.home.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mindorks.framework.mvvm.core.BuildConfig
import com.mindorks.framework.mvvm.core.data.model.api.service.ServiceResponse
import com.mindorks.framework.mvvm.databinding.ItemBlogEmptyViewBinding
import com.mindorks.framework.mvvm.databinding.ItemCourseBinding
import com.mindorks.framework.mvvm.ui.base.BaseViewHolder
import com.mindorks.framework.mvvm.ui.features.course.activity.DetailCourse
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogEmptyItemViewModel
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogEmptyItemViewModel.BlogEmptyItemViewModelListener
import com.mindorks.framework.mvvm.utils.ext.loadImage

/**
 * Created by amitshekhar on 10/07/17.
 */
class HomeListAdapter(private val mResponseList: ArrayList<ServiceResponse.Data?>?) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private var mListener: HomeListAdapterListener? = null
    var idUser = 0
    var userRoles: String? = null
    var isNotify = false
    override fun getItemCount(): Int {
        return if (mResponseList != null && mResponseList.size > 0) {
            mResponseList.size
        } else {
            1
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mResponseList != null && mResponseList.isNotEmpty()) {
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
                    ItemCourseBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                ItemListViewHolder(blogViewBinding)
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

    fun addItems(itemList: ArrayList<ServiceResponse.Data?>?) {
        if (itemList != null) {
            mResponseList!!.addAll(itemList)
        }
        notifyDataSetChanged()
    }

    fun clearItems() {
        mResponseList!!.clear()
    }

    fun setListener(listener: HomeListAdapterListener?) {
        mListener = listener
    }

    interface HomeListAdapterListener {
        fun onRetryClick()
    }

    inner class ItemListViewHolder(private val mBinding: ItemCourseBinding) : BaseViewHolder(
        mBinding.root
    ) {
        override fun onBind(position: Int) {
            val dataResponse = mResponseList!![position]
            mBinding.tvTitleCourse.text = dataResponse?.name
            mBinding.tvContent.text = dataResponse?.namaUsaha + "\n" + dataResponse?.providerName
            val imageCover =
                dataResponse!!.attchments!![0]!!.replace("http://localhost", BuildConfig.BASE_URL)
            mBinding.imvContent.loadImage(
                imageCover
            )
            mBinding.tvLabel.text = if (dataResponse.categoryId == 1) {
                "Lowongan Kerja"
            } else {
                "Magang"
            }
            mBinding.root.setOnClickListener {
                itemView.context.startActivity(
                    DetailCourse.newIntent(
                        itemView.context,
                        dataResponse.name!!,
                        dataResponse.id,
                        dataResponse.namaUsaha,
                        dataResponse.categoryName!!,
                        dataResponse.alamatLowongan,
                        dataResponse.providerPhone,
                        dataResponse.description,
                        dataResponse.jadwal,
                        dataResponse.quota,
                        0,
                        dataResponse.providerId,
                        dataResponse.providerName,
                        userRoles,
                        imageCover,
                        isNotify
                    )
                )
            }
            mBinding.executePendingBindings()
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