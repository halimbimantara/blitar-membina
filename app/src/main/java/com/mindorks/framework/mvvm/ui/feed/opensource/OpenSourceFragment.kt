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

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindorks.framework.mvvm.BR
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.databinding.FragmentOpenSourceBinding
import com.mindorks.framework.mvvm.di.component.FragmentComponent
import com.mindorks.framework.mvvm.ui.base.BaseFragment
import com.mindorks.framework.mvvm.ui.feed.opensource.OpenSourceAdapter.OpenSourceAdapterListener
import javax.inject.Inject

/**
 * Created by amitshekhar on 10/07/17.
 */
class OpenSourceFragment : BaseFragment<FragmentOpenSourceBinding?, OpenSourceViewModel?>(),
    OpenSourceNavigator, OpenSourceAdapterListener {
    var mFragmentOpenSourceBinding: FragmentOpenSourceBinding? = null

    @kotlin.jvm.JvmField
    @Inject
    var mLayoutManager: LinearLayoutManager? = null

    @kotlin.jvm.JvmField
    @Inject
    var mOpenSourceAdapter: OpenSourceAdapter? = null


    override fun handleError(throwable: Throwable?) {
        // handle error
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel!!.navigator = this
        mOpenSourceAdapter!!.setListener(this)
    }

    override fun onRetryClick() {
        mViewModel!!.fetchRepos()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFragmentOpenSourceBinding = viewDataBinding
        setUp()
    }

    override fun performDependencyInjection(buildComponent: FragmentComponent) {
        buildComponent.inject(this)
    }

    private fun setUp() {
        mLayoutManager!!.orientation = LinearLayoutManager.VERTICAL
        mFragmentOpenSourceBinding!!.openSourceRecyclerView.layoutManager = mLayoutManager
        mFragmentOpenSourceBinding!!.openSourceRecyclerView.itemAnimator = DefaultItemAnimator()
        mFragmentOpenSourceBinding!!.openSourceRecyclerView.adapter = mOpenSourceAdapter
    }

    companion object {
        fun newInstance(): OpenSourceFragment {
            val args = Bundle()
            val fragment = OpenSourceFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
       return R.layout.fragment_open_source
    }
}