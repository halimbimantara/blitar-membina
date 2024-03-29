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

package com.mindorks.framework.mvvm.ui.feedbuilder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.core.app.NavUtils;
import androidx.core.app.TaskStackBuilder;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.tabs.TabLayout;
import com.mindorks.framework.mvvm.BR;
import com.mindorks.framework.mvvm.R;
import com.mindorks.framework.mvvm.databinding.ActivityFeedBuilderBinding;
import com.mindorks.framework.mvvm.di.module.ViewModelProviderFactoryV2;
import com.mindorks.framework.mvvm.ui.base.BaseActivityOld;
import com.mindorks.framework.mvvm.ui.feed.FeedViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


/**
 * Created by amitshekhar on 10/07/17.
 */


public class FeedActivityV2 extends BaseActivityOld<ActivityFeedBuilderBinding, FeedViewModelV2> implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    FeedPagerAdapterV2 mPagerAdapter;

    @Inject
    ViewModelProviderFactoryV2 factory;

    private ActivityFeedBuilderBinding mActivityFeedBuilderBinding;
    private FeedViewModelV2 mFeedViewModel;

    public static Intent newIntent(Context context) {
        return new Intent(context, FeedActivityV2.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feed_builder;
    }

    @Override
    public FeedViewModelV2 getViewModel() {
        mFeedViewModel = new ViewModelProvider(this, factory).get(FeedViewModelV2.class);
        return mFeedViewModel;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityFeedBuilderBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mPagerAdapter =new FeedPagerAdapterV2(getSupportFragmentManager());
        setSupportActionBar(mActivityFeedBuilderBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mPagerAdapter.setCount(2);

        mActivityFeedBuilderBinding.feedViewPager.setAdapter(mPagerAdapter);

        mActivityFeedBuilderBinding.tabLayout.addTab(mActivityFeedBuilderBinding.tabLayout.newTab().setText(getString(R.string.blog)));
        mActivityFeedBuilderBinding.tabLayout.addTab(mActivityFeedBuilderBinding.tabLayout.newTab().setText(getString(R.string.open_source)));

        mActivityFeedBuilderBinding.feedViewPager.setOffscreenPageLimit(mActivityFeedBuilderBinding.tabLayout.getTabCount());

        mActivityFeedBuilderBinding.feedViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mActivityFeedBuilderBinding.tabLayout));

        mActivityFeedBuilderBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mActivityFeedBuilderBinding.feedViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        });
    }
}
