package com.mindorks.framework.mvvm.di.component

import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.di.CoreSubComponent
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import com.mindorks.framework.mvvm.di.module.DialogModule
import com.mindorks.framework.mvvm.di.scope.DialogScope
import com.mindorks.framework.mvvm.ui.main.rating.RateUsDialog
import dagger.Component

/*
 * Author: rotbolt
 */
@DialogScope
@Component(modules = [DialogModule::class], dependencies = [CoreSubComponent::class])
interface DialogComponent {
    val dataManager: DataManager
    val schedulerProvider: SchedulerProvider
    fun inject(dialog: RateUsDialog?)
}