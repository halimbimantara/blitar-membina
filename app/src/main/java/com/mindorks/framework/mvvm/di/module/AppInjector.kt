package com.mindorks.framework.mvvm.di.module

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.mindorks.framework.mvvm.MvvmApp
import com.mindorks.framework.mvvm.core.di.CoreSubComponent
import com.mindorks.framework.mvvm.di.component.DaggerAppComponent
import dagger.android.AndroidInjection

object AppInjector {
    fun init(app: MvvmApp, coreSubComponent: CoreSubComponent) {
        DaggerAppComponent
            .builder().coreComponent(coreSubComponent)
            .application(app)
            .build()
            .inject(app)


        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                handleActivity(activity)
            }

        })
    }

    private fun handleActivity(activity: Activity) {
        if (activity is Injectable) {
            AndroidInjection.inject(activity)
        }

    }
}