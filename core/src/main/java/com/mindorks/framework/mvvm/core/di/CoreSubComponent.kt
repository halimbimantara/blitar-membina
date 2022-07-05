package com.mindorks.framework.mvvm.core.di

import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import dagger.Subcomponent

@Subcomponent
interface CoreSubComponent {
    fun getDataManager(): DataManager
    fun getSchedulerProvider(): SchedulerProvider
}