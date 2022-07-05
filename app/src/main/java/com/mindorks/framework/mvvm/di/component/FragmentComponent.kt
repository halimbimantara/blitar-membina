package com.mindorks.framework.mvvm.di.component

import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.di.CoreSubComponent
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import com.mindorks.framework.mvvm.di.module.FragmentModule
import com.mindorks.framework.mvvm.di.scope.FragmentScope
import com.mindorks.framework.mvvm.ui.about.AboutFragment
import com.mindorks.framework.mvvm.ui.feed.blogs.BlogFragment
import com.mindorks.framework.mvvm.ui.feed.opensource.OpenSourceFragment
import com.mindorks.framework.mvvm.ui.home.ui.home.HomeFragment
import com.mindorks.framework.mvvm.ui.home.ui.notifications.NotificationsFragment
import dagger.Component

/*
 * Author: rotbolt
 */
@FragmentScope
@Component(
    dependencies = [CoreSubComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {
    val dataManager: DataManager
    val schedulerProvider: SchedulerProvider
    fun inject(fragment: BlogFragment?)
    fun inject(fragment: OpenSourceFragment?)
    fun inject(fragment: AboutFragment?)
    fun inject(fragment: HomeFragment?)
    fun inject(fragment: NotificationsFragment?)
}