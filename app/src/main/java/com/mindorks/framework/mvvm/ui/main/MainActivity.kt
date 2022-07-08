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
package com.mindorks.framework.mvvm.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.mindorks.framework.mvvm.BR
import com.mindorks.framework.mvvm.BuildConfig
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.databinding.ActivityMainBinding
import com.mindorks.framework.mvvm.databinding.NavHeaderMainBinding
import com.mindorks.framework.mvvm.di.component.ActivityComponent
import com.mindorks.framework.mvvm.ui.about.AboutFragment
import com.mindorks.framework.mvvm.ui.base.BaseActivity
import com.mindorks.framework.mvvm.ui.feed.FeedActivity
import com.mindorks.framework.mvvm.ui.account.login.LoginActivity
import com.mindorks.framework.mvvm.ui.main.rating.RateUsDialog
import com.mindorks.framework.mvvm.utils.ScreenUtils
import com.mindorks.placeholderview.SwipeDecor
import com.mindorks.placeholderview.SwipePlaceHolderView

class MainActivity : BaseActivity<ActivityMainBinding?, MainViewModel?>(), MainNavigator {
    private var mActivityMainBinding: ActivityMainBinding? = null
    private var mCardsContainerView: SwipePlaceHolderView? = null
    private var mDrawer: DrawerLayout? = null
    private var mNavigationView: NavigationView? = null
    private var mToolbar: Toolbar? = null


    override fun handleError(throwable: Throwable?) {
        // handle error
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(AboutFragment.Companion.TAG)
        if (fragment == null) {
            super.onBackPressed()
        } else {
            onFragmentDetached(AboutFragment.Companion.TAG)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onFragmentDetached(tag: String) {
        val fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            fragmentManager
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .remove(fragment)
                .commitNow()
            unlockDrawer()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val drawable = item.icon
        if (drawable is Animatable) {
            (drawable as Animatable).start()
        }
        return when (item.itemId) {
            R.id.action_cut -> true
            R.id.action_copy -> true
            R.id.action_share -> true
            R.id.action_delete -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun openLoginActivity() {
        startActivity(LoginActivity.Companion.newIntent(this))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = viewDataBinding
        mViewModel!!.navigator = this
        setUp()
    }

    override fun performDependencyInjection(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        if (mDrawer != null) {
            mDrawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    private fun lockDrawer() {
        if (mDrawer != null) {
            mDrawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }
    }

    private fun setUp() {
        mDrawer = mActivityMainBinding!!.drawerView
        mToolbar = mActivityMainBinding!!.toolbar
        mNavigationView = mActivityMainBinding!!.navigationView
        mCardsContainerView = mActivityMainBinding!!.cardsContainer
        setSupportActionBar(mToolbar)
        val mDrawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            mDrawer,
            mToolbar,
            R.string.open_drawer,
            R.string.close_drawer
        ) {
            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                hideKeyboard()
            }
        }
        mDrawer!!.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
        setupNavMenu()
        val version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME
        mViewModel!!.updateAppVersion(version)
        mViewModel!!.onNavMenuCreated()
        setupCardContainerView()
        subscribeToLiveData()
    }

    private fun setupCardContainerView() {
        val screenWidth = ScreenUtils.getScreenWidth(this)
        val screenHeight = ScreenUtils.getScreenHeight(this)
        mCardsContainerView!!.builder
            .setDisplayViewCount(3)
            .setHeightSwipeDistFactor(10f)
            .setWidthSwipeDistFactor(5f)
            .setSwipeDecor(
                SwipeDecor()
                    .setViewWidth((0.90 * screenWidth).toInt())
                    .setViewHeight((0.75 * screenHeight).toInt())
                    .setPaddingTop(20)
                    .setSwipeRotationAngle(10)
                    .setRelativeScale(0.01f)
            )
        mCardsContainerView!!.addItemRemoveListener { count: Int ->
            if (count == 0) {
                // reload the contents again after 1 sec delay
                Handler(mainLooper).postDelayed({
                    //Reload once all the cards are removed
                    mViewModel!!.loadQuestionCards()
                }, 800)
            } else {
                mViewModel!!.removeQuestionCard()
            }
        }
    }

    private fun setupNavMenu() {
        val navHeaderMainBinding: NavHeaderMainBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.nav_header_main, mActivityMainBinding!!.navigationView, false
        )
        mActivityMainBinding!!.navigationView.addHeaderView(navHeaderMainBinding.root)
        navHeaderMainBinding.viewModel = mViewModel
        mNavigationView!!.setNavigationItemSelectedListener { item: MenuItem ->
            mDrawer!!.closeDrawer(GravityCompat.START)
            when (item.itemId) {
                R.id.navItemAbout -> {
                    showAboutFragment()
                    return@setNavigationItemSelectedListener true
                }
                R.id.navItemRateUs -> {
                    RateUsDialog.newInstance().show(supportFragmentManager)
                    return@setNavigationItemSelectedListener true
                }
                R.id.navItemFeed -> {
                    startActivity(FeedActivity.newIntent(this@MainActivity))
                    return@setNavigationItemSelectedListener true
                }
                R.id.navItemLogout -> {
                    mViewModel!!.logout()
                    return@setNavigationItemSelectedListener true
                }
                else -> return@setNavigationItemSelectedListener false
            }
        }
    }

    private fun showAboutFragment() {
        lockDrawer()
        supportFragmentManager
            .beginTransaction()
            .disallowAddToBackStack()
            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
            .add(
                R.id.clRootView,
                AboutFragment.Companion.newInstance(),
                AboutFragment.Companion.TAG
            )
            .commit()
    }

    private fun subscribeToLiveData() {

    }

    private fun unlockDrawer() {
        if (mDrawer != null) {
            mDrawer!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun observeChange() {

    }

}