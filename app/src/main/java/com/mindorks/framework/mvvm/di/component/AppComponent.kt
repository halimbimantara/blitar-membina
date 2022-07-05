package com.mindorks.framework.mvvm.di.component

import android.app.Application
import com.mindorks.framework.mvvm.MvvmApp
import com.mindorks.framework.mvvm.core.di.CoreSubComponent
import com.mindorks.framework.mvvm.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule


@Component(
    dependencies = [CoreSubComponent::class],
    modules = [
        AndroidInjectionModule::class,ActivityBuilder::class
    ]
)
@AppScope
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun coreComponent(coreComponent: CoreSubComponent): Builder
        fun build(): AppComponent
    }

    fun inject(instance: MvvmApp)

}