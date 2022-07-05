package com.mindorks.framework.mvvm.core.di

import android.app.Application
import android.content.Context
import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.di.modules.AppModule
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import dagger.Component
import javax.inject.Singleton


/**
 * Core component that all module's components depend on.
 * ContextModule::class
 */
@Singleton
@Component(modules = [AppModule::class, ContextModule::class])
interface CoreComponent {
    /*
     * The methods below are sort of a 'promise' that this Component can provide these objects to dependent Components.
     * This is done because we cannot use the sub-components (hard coded connection) with our (dynamic) feature modules.
     */
    fun inject(application: Application)
    val context: Context
    val coreSubcomponent: CoreSubComponent
    fun getDataManager(): DataManager
    fun getSchedulerProvider(): SchedulerProvider
}