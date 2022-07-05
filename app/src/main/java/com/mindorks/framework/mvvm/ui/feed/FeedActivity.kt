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
package com.mindorks.framework.mvvm.ui.feed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.NavUtils
import androidx.core.app.TaskStackBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.mindorks.framework.mvvm.BR
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.databinding.ActivityFeedBinding
import com.mindorks.framework.mvvm.di.component.ActivityComponent
import com.mindorks.framework.mvvm.ui.base.BaseActivity
import javax.inject.Inject

class FeedActivity : BaseActivity<ActivityFeedBinding?, FeedViewModel?>() {
    @kotlin.jvm.JvmField
    @Inject
    var mPagerAdapter: FeedPagerAdapter? = null
    private var mActivityFeedBinding: ActivityFeedBinding? = null


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Respond to the action bar's Up/Home button
        if (item.itemId == android.R.id.home) {
            val upIntent = NavUtils.getParentActivityIntent(this)
            upIntent!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                // This activity is NOT part of this app's task, so create a new task
                // when navigating up, with a synthesized back stack.
                TaskStackBuilder.create(this) // Add all of this activity's parents to the back stack
                    .addNextIntentWithParentStack(upIntent) // Navigate up to the closest parent
                    .startActivities()
            } else {
                // This activity is part of this app's task, so simply
                // navigate up to the logical parent activity.
                NavUtils.navigateUpTo(this, upIntent)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityFeedBinding = viewDataBinding
        setUp()
    }

    override fun performDependencyInjection(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    private fun setUp() {
        setSupportActionBar(mActivityFeedBinding!!.toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
        mPagerAdapter!!.count = 2
        mActivityFeedBinding!!.feedViewPager.adapter = mPagerAdapter
        mActivityFeedBinding!!.tabLayout.addTab(
            mActivityFeedBinding!!.tabLayout.newTab().setText(getString(R.string.blog))
        )
        mActivityFeedBinding!!.tabLayout.addTab(
            mActivityFeedBinding!!.tabLayout.newTab().setText(getString(R.string.open_source))
        )
        mActivityFeedBinding!!.feedViewPager.offscreenPageLimit =
            mActivityFeedBinding!!.tabLayout.tabCount
        mActivityFeedBinding!!.feedViewPager.addOnPageChangeListener(
            TabLayoutOnPageChangeListener(
                mActivityFeedBinding!!.tabLayout
            )
        )
        mActivityFeedBinding!!.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {}
            override fun onTabSelected(tab: TabLayout.Tab) {
                mActivityFeedBinding!!.feedViewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
        })
    }

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, FeedActivity::class.java)
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_feed

    override fun observeChange() {}
}