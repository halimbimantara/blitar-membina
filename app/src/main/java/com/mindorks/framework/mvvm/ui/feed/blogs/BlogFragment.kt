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

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.framework.mvvm.BR
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.core.data.model.api.BlogResponse.Blog
import com.mindorks.framework.mvvm.databinding.FragmentBlogBinding
import com.mindorks.framework.mvvm.di.component.FragmentComponent
import com.mindorks.framework.mvvm.ui.base.BaseFragment
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogAdapter.BlogAdapterListener
import javax.inject.Inject

/**
 * Created by amitshekhar on 10/07/17.
 */
class BlogFragment : BaseFragment<FragmentBlogBinding?, BlogViewModel?>(), BlogNavigator,
    BlogAdapterListener {
    @kotlin.jvm.JvmField
    @Inject
    var mBlogAdapter: BlogAdapter? = null
    var mFragmentBlogBinding: FragmentBlogBinding? = null

    @kotlin.jvm.JvmField
    @Inject
    var mLayoutManager: LinearLayoutManager? = null


    override fun handleError(throwable: Throwable?) {
        // handle error
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel!!.navigator = this
        mBlogAdapter!!.setListener(this)
    }

    override fun onRetryClick() {
        mViewModel!!.fetchBlogs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentBlogBinding = viewDataBinding
        setUp()
    }

    override fun performDependencyInjection(buildComponent: FragmentComponent) {
        buildComponent.inject(this)
    }

    override fun updateBlog(blogList: List<Blog>?) {
        mBlogAdapter!!.addItems(blogList)
    }

    private fun setUp() {
        mLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        mFragmentBlogBinding!!.blogRecyclerView.layoutManager = mLayoutManager
        mFragmentBlogBinding!!.blogRecyclerView.itemAnimator = DefaultItemAnimator()
        mFragmentBlogBinding!!.blogRecyclerView.adapter = mBlogAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(): BlogFragment {
            val args = Bundle()
            val fragment = BlogFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_blog
    }
}