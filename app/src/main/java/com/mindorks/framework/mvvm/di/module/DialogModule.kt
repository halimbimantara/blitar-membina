package com.mindorks.framework.mvvm.di.module

import androidx.core.util.Supplier
import androidx.lifecycle.ViewModelProvider
import com.mindorks.framework.mvvm.ViewModelProviderFactory
import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider
import com.mindorks.framework.mvvm.ui.base.BaseDialog
import com.mindorks.framework.mvvm.ui.main.rating.RateUsViewModel
import dagger.Module
import dagger.Provides

/*
 * Author: rotbolt
 */
@Module
class DialogModule(private val dialog: BaseDialog) {
    @Provides
    fun provideRateUsViewModel(
        dataManager: DataManager,
        schedulerProvider: SchedulerProvider
    ): RateUsViewModel {
        val supplier = Supplier { RateUsViewModel(dataManager, schedulerProvider) }
        val factory = ViewModelProviderFactory(
            RateUsViewModel::class.java, supplier
        )
        return ViewModelProvider(dialog.requireActivity(), factory).get(RateUsViewModel::class.java)
    }
}