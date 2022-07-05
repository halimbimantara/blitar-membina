package com.mindorks.framework.mvvm.ui.feed

import com.mindorks.framework.mvvm.core.data.DataManager
import com.mindorks.framework.mvvm.ui.base.BaseViewModel
import com.mindorks.framework.mvvm.core.utils.rx.SchedulerProvider

/**
 * Created by Jyoti on 29/07/17.
 */
class FeedViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider) :
    BaseViewModel<Any?>(dataManager, schedulerProvider)