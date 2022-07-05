package com.mindorks.framework.mvvm.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mindorks.framework.mvvm.R
import com.mindorks.framework.mvvm.databinding.ActivityHomeMainBinding
import com.mindorks.framework.mvvm.di.component.ActivityComponent
import com.mindorks.framework.mvvm.ui.base.BaseActivity
import com.mindorks.framework.mvvm.ui.home.ui.HomeMainViewModel

class HomeActivityMain : BaseActivity<ActivityHomeMainBinding?, HomeMainViewModel?>() {

    lateinit var binding: ActivityHomeMainBinding
    override val bindingVariable: Int
        get() = 0

    override val layoutId: Int
        get() = R.layout.activity_home_main

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, HomeActivityMain::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = viewDataBinding!!
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_home_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun performDependencyInjection(buildComponent: ActivityComponent) {
        buildComponent.inject(this)
    }

    override fun observeChange() {

    }

}