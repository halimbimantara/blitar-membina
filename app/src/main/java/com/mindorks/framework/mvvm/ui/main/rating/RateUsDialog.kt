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
package com.mindorks.framework.mvvm.ui.main.rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.databinding.DialogRateUsBinding
import com.mindorks.framework.mvvm.di.component.DaggerDialogComponent
import com.mindorks.framework.mvvm.di.component.DialogComponent
import com.mindorks.framework.mvvm.di.module.DialogModule
import com.mindorks.framework.mvvm.ui.base.BaseDialog
import javax.inject.Inject

/**
 * Created by amitshekhar on 10/07/17.
 */
class RateUsDialog : BaseDialog(), RateUsCallback {
    @kotlin.jvm.JvmField
    @Inject
    var mRateUsViewModel: RateUsViewModel? = null
    override fun dismissDialog() {
        dismissDialog(TAG)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: DialogRateUsBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_rate_us, container, false)
        val view = binding.root
        performDependencyInjection(buildComponent)
        binding.viewModel = mRateUsViewModel
        mRateUsViewModel!!.navigator = this
        return view
    }

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, TAG)
    }

    private val buildComponent: DialogComponent
        private get() = DaggerDialogComponent.builder()
            .dialogModule(DialogModule(this))
            .build()

    private fun performDependencyInjection(buildComponent: DialogComponent) {
        buildComponent.inject(this)
    }

    companion object {
        private const val TAG = "RateUsDialog"
        fun newInstance(): RateUsDialog {
            val fragment = RateUsDialog()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }
}